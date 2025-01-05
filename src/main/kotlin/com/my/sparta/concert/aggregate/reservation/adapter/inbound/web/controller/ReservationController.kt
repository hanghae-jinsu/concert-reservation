package com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.controller

import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping("/reservation")
class ReservationController {
    @PostMapping
    fun reserveConcert() {
        // TODO: 유저가 좌석을 조회하고  콘서트를 예약한다.
    }
}
