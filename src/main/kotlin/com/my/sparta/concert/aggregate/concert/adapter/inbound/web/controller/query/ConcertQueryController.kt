package com.my.sparta.concert.aggregate.concert.adapter.inbound.web.controller.query

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
class ConcertQueryController() {
    @GetMapping("{concertId}/reserve")
    fun getReservableSeats() {
        // TODO:  콘서트를 예매가능한 콘서트 좌석을 조회한다.
    }
}
