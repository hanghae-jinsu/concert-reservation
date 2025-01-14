package com.my.sparta.concert.aggregate.user.adapter.inbound.controller

import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.GetTokenResponse
import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.UserWalletInfoResponse
import com.my.sparta.concert.aggregate.user.adapter.inbound.mapper.UserWebMapper
import com.my.sparta.concert.aggregate.user.application.port.inbound.GenerateTokenUseCase
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class UserQueryController(
    val generateTokenUseCase: GenerateTokenUseCase,
    val loadUserInfoPort: LoadUserInfoPort,
    val userWebMapper: UserWebMapper,
) {
    @Operation(summary = "유저 토큰을 발급한다.", description = "유저 토큰을 발급한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "회원으로 등록된 userId가 아닙니다."),
        ],
    )
    @Tag(name = "user", description = "user")
    @GetMapping("/public/token/{userId}")
    fun getToken(
        @PathVariable userId: String,
    ): ResponseEntity<GetTokenResponse> {
        val userToken = generateTokenUseCase.generateToken(userId)
        return ResponseEntity.ok(GetTokenResponse(userToken))
    }

    @Operation(summary = "내 남은 잔고가 얼마인지 확인", description = "내 잔고정보를 반환한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "등록되어있는 유저의 id가 아닙니다."),
        ],
    )
    @GetMapping("/user/money/{userId}")
    fun findChargedMoney(
        @PathVariable("userId") userId: String,
    ): ResponseEntity<UserWalletInfoResponse> {
        val userInfoById = loadUserInfoPort.getUserInfoById(userId)
        return ResponseEntity.ok(userWebMapper.mapToResponse(userInfoById))
    }
}
