package com.my.sparta.concert.aggregate.user.adapter.inbound.web.interfaces.response

import lombok.Data
import java.math.BigDecimal

@Data
class UserWalletInfoResponse(
    val userId: String,
    val money: BigDecimal,
)
