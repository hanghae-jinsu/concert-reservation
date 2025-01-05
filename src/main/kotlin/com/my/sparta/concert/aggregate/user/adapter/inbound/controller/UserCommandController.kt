package com.my.sparta.concert.aggregate.user.adapter.inbound.controller

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserCommandController {

    @GetMapping("/token/{userId}")
    fun getToken(
    ) {
        TODO("토큰을 발급한다.")
    }

    @GetMapping("/charge/{userId}")
    fun findChargedMoney() {
        TODO("해당 유저의 잔고를 조회하는 API")
    }

    @PostMapping("/charge/{userId}")
    fun chargeMoney() {

        TODO("해당 유저 잔고를 충전한다.")
    }

}
