package com.my.sparta.concert.aggregate.concert.adapter.inbound.web.controller.query

import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response.ConcertScheduleResponse
import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.mapper.ConcertScheduleWebMapper
import com.my.sparta.concert.aggregate.concert.application.port.outbound.GetConcertScheduleInfoPort
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
class ConcertQueryController(
    private val getConcertScheduleInfoPort: GetConcertScheduleInfoPort,
    private val concertScheduleWebMapper: ConcertScheduleWebMapper,
) {
    @Operation(
        operationId = "getReservableSeats",
        summary = "콘서트 예약 가능 목록 조회",
        description = "콘서트 예약 가능 목록을 조회합니다.",
        parameters = [
            Parameter(
                name = "concertId",
                `in` = ParameterIn.PATH,
                required = true,
                description = "concertId",
                example = "concert12345",
            ),
        ],
        tags = ["Concert API"],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "예약 가능한 콘서트 리스트",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ConcertScheduleResponse::class),
                    ),
                ],
            ),
            ApiResponse(responseCode = "400", description = "Bad Request"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "500", description = "Internal Server Error"),
        ],
    )
// concertSchedule 50명 차면 인원 마감. case 생각
    @GetMapping("{concertId}/reserve")
    fun getReservableSeats(
        @PathVariable("concertId") concertId: String,
    ): ResponseEntity<ConcertScheduleResponse> {
        val concertScheduleList = getConcertScheduleInfoPort.getConcertScheduleById(concertId)
        return ResponseEntity.ok(concertScheduleWebMapper.mapToListResponse(concertScheduleList))
    }
}
