package com.my.sparta.concert.aggregate.integration

import com.my.sparta.concert.aggregate.concert.application.port.inbound.SaveConcertInfoUseCase
import com.my.sparta.concert.aggregate.concert.application.port.outbound.GetConcertScheduleInfoPort
import com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.interfaces.ConcertReservationRequest
import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper.ReserveWebMapper
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.ReserveConcertUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SavePaymentInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveReservationPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.BuyIngTicketUserUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
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

        val concertInfo = loadConcertPort.getConcertInfoByName("라_트라비아타")
        val concertScheduleInfo = getConcertScheduleInfoPort.getConcertScheduleById(concertInfo.concertId)

        (1..80).forEach { i ->
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

        // 2) concertRequests -> command 리스트 변환 후, addAll()로 채우기
        commandRequest.addAll(
            concertRequests.map { request ->
                reserveWebMapper.mapToCommand(request)
            }
        )
    }

    @Test
    fun `동시성 이슈 콘서트 예약`() {

        val threadCount = 80
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

                } finally {
                    latch.countDown()
                    failureCount.incrementAndGet()
                }
            }
        }

        executor.shutdown()
        executor.awaitTermination(1, TimeUnit.MINUTES)


        assertThat(successCount.get()).isEqualTo(80)
        assertThat(failureCount.get()).isEqualTo(10)

    }


}
