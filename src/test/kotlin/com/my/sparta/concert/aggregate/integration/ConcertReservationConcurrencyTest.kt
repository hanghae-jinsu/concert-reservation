package com.my.sparta.concert.aggregate.integration

import com.my.sparta.concert.aggregate.concert.application.port.outbound.GetConcertScheduleInfoPort
import com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.interfaces.ConcertReservationRequest
import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper.ReserveWebMapper
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.ReserveConcertUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertPort
import jakarta.persistence.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConcertReservationConcurrencyTest(

    @Autowired private val loadConcertPort: LoadConcertPort,
    @Autowired private val getConcertScheduleInfoPort: GetConcertScheduleInfoPort,
    @Autowired private val reserveConcertUseCase: ReserveConcertUseCase,
    @Autowired private val reserveWebMapper: ReserveWebMapper,
) {


    private val concertRequests = mutableListOf<ConcertReservationRequest>()
    private val commandRequest = mutableListOf<ConcertReservationCommand>()

    @BeforeEach
    fun setUp() {

        val concertInfo = loadConcertPort.getConcertInfoByName("해리포터와 마법사의 돌")
        val concertScheduleInfo = getConcertScheduleInfoPort.getConcertScheduleById(concertInfo.concertId)

        (1..50).forEach { i ->
            concertRequests.add(
                ConcertReservationRequest(
                    userId = "user5",
                    concertId = concertInfo.concertId,
                    concertScheduleId = concertScheduleInfo[0].concertScheduleId,
                    concertSeatNumber = i,
                    count = 1,
                    paymentType = "CARD"
                )
            )
        }

        commandRequest.addAll(
            concertRequests.map { request ->
                reserveWebMapper.mapToCommand(request)
            }
        )
    }

    @Test
    fun `동시성 이슈 콘서트 예약`() {

        val threadCount = 50
        val successTarget = 50
        val latch = CountDownLatch(threadCount)
        val executor = Executors.newFixedThreadPool(threadCount)
        // 성공/실패 카운트
        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        (1..threadCount).forEach { i ->
            executor.submit {
                try {
                    // 예: i번째 콘서트 예약 요청 처리
                    val request = commandRequest[i - 1]
                    reserveConcertUseCase.reserve(request)
                    successCount.incrementAndGet()

                } catch (e: Exception) {
                    println(e.message)
                    failureCount.incrementAndGet() // 실패 시 증가
                } finally {
                    latch.countDown()
                }
            }
        }

        executor.shutdown()
        executor.awaitTermination(1, TimeUnit.MINUTES)


        assertThat(successCount.get()).isEqualTo(successTarget) // 성공 수 검증
        assertThat(failureCount.get()).isEqualTo(threadCount - successTarget) // 실패 수 검증

    }

    @Test
    fun `좌석 예약중인 좌석은 다른 요청이 들어와도 접근할 수 없어야한다`() {

        // given
        val request1 = commandRequest[3]
        reserveConcertUseCase.reserve(request1) // 첫 번째 예약 시도 (성공)

        // when & then
        val request2 = commandRequest[3] // 동일한 좌석 ID로 두 번째 예약 시도
        val exception = assertThrows<EntityExistsException> {
            reserveConcertUseCase.reserve(request2)
        }

        // 예외 메시지 검증
        assertEquals("해당하는 id ${request2.concertSeatNumber} 는 이미 예약된 좌석 입니다.", exception.message)

    }


}
