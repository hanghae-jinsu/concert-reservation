package com.my.sparta.concert.aggregate.user.application.port.inbound.command

import java.math.BigDecimal

data class UserUseMoneyCommand(
    val userId: String,
    val paidAmount: BigDecimal,
)
