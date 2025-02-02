package com.my.sparta.concert.aggregate.user.adapter.inbound.web.interfaces.request

import lombok.Data
import java.math.BigDecimal

@Data
class UserChargeMoneyRequest(
    val userId: String,
    val money: BigDecimal,
    val paymentType: String,
)
