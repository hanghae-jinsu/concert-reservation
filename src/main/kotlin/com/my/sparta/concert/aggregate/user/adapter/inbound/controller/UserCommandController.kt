package com.my.sparta.concert.aggregate.user.adapter.inbound.controller

import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.GetTokenResponse
import com.my.sparta.concert.aggregate.user.application.port.inbound.GenerateTokenUseCase
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserCommandController(
    val generateTokenUseCase: GenerateTokenUseCase,
) {
    @GetMapping("/token/{userId}")
    fun getToken(
        @PathVariable userId: String,
    ): ResponseEntity<GetTokenResponse> {
        val userToken = generateTokenUseCase.generateToken(userId)
        return ResponseEntity.ok(GetTokenResponse(userToken))
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
