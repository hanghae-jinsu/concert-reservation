package com.my.sparta.concert.aggregate.user.adapter.inbound.controller

import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.request.UserChargeMoneyRequest
import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.UserWalletInfoResponse
import com.my.sparta.concert.aggregate.user.adapter.inbound.mapper.UserWebMapper
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserChargeMoneyUseCase
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
    val userWebMapper: UserWebMapper
) {

    @PostMapping("/money/{userId}")
    fun chargeMoney(
        @RequestBody request: UserChargeMoneyRequest
    ): ResponseEntity<UserWalletInfoResponse> {
        val chargeCommand = userWebMapper.mapToCommand(request)
        val chargeMoney = userChargeMoneyUseCase.chargeMoney(chargeCommand);

        return ResponseEntity.ok(UserWalletInfoResponse(chargeMoney.userId, chargeMoney.wallet.money))

    }

}
