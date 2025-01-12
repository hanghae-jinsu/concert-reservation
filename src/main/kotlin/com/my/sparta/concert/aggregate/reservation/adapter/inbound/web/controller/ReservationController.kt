package com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.controller

import com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.interfaces.ConcertReservationRequest
import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper.ReserveWebMapper
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.ReserveConcertUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/reservation")
class ReservationController(
    private val reserveConcertUseCase: ReserveConcertUseCase,
    private val reserveWebMapper: ReserveWebMapper,
) {
    @Operation(summary = "해당 콘서트를 예약한다.", description = "콘서트를 예약한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "등록되어있는 유저의 id가 아닙니다."),
        ],
    )
    @PostMapping
    fun reserveConcert(
        @RequestBody request: ConcertReservationRequest,
    ): ResponseEntity<*> {
        val concertReservationCommand = reserveWebMapper.mapToCommand(request)
        val concertReservationDetailInfo = reserveConcertUseCase.reserve(concertReservationCommand)

        return ResponseEntity.ok(concertReservationDetailInfo)
    }
}
