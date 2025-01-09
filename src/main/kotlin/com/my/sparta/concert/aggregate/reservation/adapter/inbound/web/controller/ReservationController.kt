package com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.controller

import com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.interfaces.ConcertReservationRequest
import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper.ReserveWebMapper
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.ReserveConcertUseCase
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
    private val reserveWebMapper: ReserveWebMapper
) {

    @PostMapping
    fun reserveConcert(
        @RequestBody request: ConcertReservationRequest
    ): ResponseEntity<*> {

        val concertReservationCommand = reserveWebMapper.mapToCommand(request);
        val concertReservationDetailInfo = reserveConcertUseCase.reserve(concertReservationCommand);

        return ResponseEntity.ok(concertReservationDetailInfo);
    }
}
