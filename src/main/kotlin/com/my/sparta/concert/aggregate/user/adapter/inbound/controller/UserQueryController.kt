package com.my.sparta.concert.aggregate.user.adapter.inbound.controller

import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.GetTokenResponse
import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.UserWalletInfoResponse
import com.my.sparta.concert.aggregate.user.adapter.inbound.mapper.UserWebMapper
import com.my.sparta.concert.aggregate.user.application.port.inbound.GenerateTokenUseCase
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserQueryController(
    val generateTokenUseCase: GenerateTokenUseCase,
    val loadUserInfoPort: LoadUserInfoPort,
    val userWebMapper: UserWebMapper
) {

    @GetMapping("/token/{userId}")
    fun getToken(
        @PathVariable userId: String,
    ): ResponseEntity<GetTokenResponse> {
        val userToken = generateTokenUseCase.generateToken(userId)
        return ResponseEntity.ok(GetTokenResponse(userToken))
    }

    @GetMapping("/money/{userId}")
    fun findChargedMoney(
        @PathVariable("userId") userId: String,
    ): ResponseEntity<UserWalletInfoResponse> {

        val userInfoById = loadUserInfoPort.getUserInfoById(userId)
        return ResponseEntity.ok(userWebMapper.mapToResponse(userInfoById))

    }

}
