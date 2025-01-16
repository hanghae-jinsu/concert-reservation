package com.my.sparta.concert.aggregate.concert.adapter.inbound.web.controller.query

import com.github.dockerjava.api.exception.NotFoundException
import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response.ConcertInfoResponse
import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response.ConcertScheduleResponse
import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.mapper.ConcertScheduleWebMapper
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSchedule
import com.my.sparta.concert.aggregate.concert.application.port.outbound.GetConcertScheduleInfoPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime


@WebMvcTest(controllers = [ConcertQueryController::class])
class ConcertQueryControllerTest(
    @Autowired val mockMvc: MockMvc,

    @MockBean val getConcertScheduleInfoPort: GetConcertScheduleInfoPort,
    @MockBean val concertScheduleWebMapper: ConcertScheduleWebMapper,
) : BehaviorSpec({
    extensions(SpringExtension)

    Given("유효한 concertId로 예약 가능한 좌석 목록을 조회할 때") {
        val concertId = "concert12345"

        // 모킹할 ConcertSchedule 리스트 생성
        val mockConcertScheduleList = listOf(
            ConcertSchedule(
                concertScheduleId = "concertScheduleId1",
                concertName = "Rock Concert",
                startDateTime = LocalDateTime.of(2025, 5, 20, 18, 0),
                endDateTime = LocalDateTime.of(2025, 5, 20, 21, 0),
                runningTime = 180,
                notice = "No refunds",
                concertId = "concert_1",
                hallId = "A",
                concertSeat = listOf() // 필요 시 좌석 추가
            )
        )

        // 모킹할 ConcertScheduleResponse 생성
        val mockConcertScheduleResponse = ConcertScheduleResponse(
            concertScheduleList = listOf(
                ConcertInfoResponse(
                    concertScheduleId = "concertScheduleId1",
                    concertName = "Rock Concert",
                    startDateTime = mockConcertScheduleList[0].startDateTime,
                    endDateTime = mockConcertScheduleList[0].endDateTime,
                    runningTime = mockConcertScheduleList[0].runningTime,
                    concertSeat = listOf()
                )
            )
        )

        // 의존성 모킹 설정
        every { getConcertScheduleInfoPort.getConcertScheduleById(concertId) } returns mockConcertScheduleList
        every { concertScheduleWebMapper.mapToListResponse(mockConcertScheduleList) } returns mockConcertScheduleResponse

        When("GET /concert/{concertId}/reserve 요청을 보내면") {
            val response = mockMvc.get("/concert/{concertId}/reserve", concertId) {
                contentType = MediaType.APPLICATION_JSON
            }

            Then("응답 상태는 200 OK이고, ConcertScheduleResponse를 반환해야 한다") {
                response.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.concertScheduleList[0].scheduleId") { value(1) }
                    jsonPath("$.concertScheduleList[0].name") { value("Rock Concert") }
                    jsonPath("$.concertScheduleList[0].startDateTime") { value("2025-05-20T18:00:00") }
                    jsonPath("$.concertScheduleList[0].endDateTime") { value("2025-05-20T21:00:00") }
                    jsonPath("$.concertScheduleList[0].runningTime") { value(10800) } // Duration을 초 단위로 반환한다고 가정
                    jsonPath("$.concertScheduleList[0].notice") { value("No refunds") }
                    jsonPath("$.concertScheduleList[0].concertId") { value(100) }
                    jsonPath("$.concertScheduleList[0].concertHallId") { value(10) }
                }
            }
        }

        And("좌석 데이터가 존재할 경우") {
            // 추가적인 좌석 데이터가 있는 경우를 테스트할 수 있습니다.
            // 예시로 좌석 데이터를 추가하고 검증하는 테스트를 작성할 수 있습니다.
        }
    }

    Given("존재하지 않는 concertId로 예약 가능한 좌석 목록을 조회할 때") {
        val invalidConcertId = "invalidConcertId"

        // 의존성 모킹 설정: NotFoundException 발생
        every { getConcertScheduleInfoPort.getConcertScheduleById(invalidConcertId) } throws NotFoundException("Concert not found")

        When("GET /concert/{concertId}/reserve 요청을 보내면") {
            val response = mockMvc.get("/concert/{concertId}/reserve", invalidConcertId) {
                contentType = MediaType.APPLICATION_JSON
            }

            Then("응답 상태는 404 Not Found를 반환해야 한다") {
                response.andExpect {
                    status { isNotFound() }
                }
            }
        }
    }


})

