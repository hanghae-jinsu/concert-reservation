package com.my.sparta.concert.aggregate.user.adapter.inbound.controller

import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.request.UserChargeMoneyRequest
import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.UserWalletInfoResponse
import com.my.sparta.concert.aggregate.user.adapter.inbound.mapper.UserWebMapper
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserChargeMoneyUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserCommandController(
    val userChargeMoneyUseCase: UserChargeMoneyUseCase,
    val userWebMapper: UserWebMapper,
) {
    @Operation(summary = "해당 유저 잔고를 충전한다.", description = "유저의 잔고를 추가한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "등록되어있는 유저의 id가 아닙니다."),
        ],
    )
    @Tag(name = "money", description = "money")
    @PostMapping("/money/{userId}")
    fun chargeMoney(
        @RequestBody request: UserChargeMoneyRequest,
    ): ResponseEntity<UserWalletInfoResponse> {
        val chargeCommand = userWebMapper.mapToCommand(request)
        val chargeMoney = userChargeMoneyUseCase.chargeMoney(chargeCommand)

        return ResponseEntity.ok(UserWalletInfoResponse(chargeMoney.userId, chargeMoney.wallet.money))
    }
}
