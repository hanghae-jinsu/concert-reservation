package com.my.sparta.concert.aggregate.user.adapter.inbound.mapper

import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.request.UserChargeMoneyRequest
import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.UserWalletInfoResponse
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserChargeCommand
import org.springframework.stereotype.Component

@Component
class UserWebMapper {

    fun mapToCommand(request: UserChargeMoneyRequest): UserChargeCommand {
        return UserChargeCommand(
            request.userId,
            Wallet(PaymentType.valueOf(request.paymentType), request.money)

        )
    }

    fun mapToResponse(domain: Users): UserWalletInfoResponse {
        return UserWalletInfoResponse(
            domain.userId,
            domain.wallet.money
        )
    }

}
