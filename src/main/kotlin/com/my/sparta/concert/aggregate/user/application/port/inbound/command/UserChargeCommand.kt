package com.my.sparta.concert.aggregate.user.application.port.inbound.command

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet

data class UserChargeCommand(
    val userId: String,
    val wallet: Wallet,
)
