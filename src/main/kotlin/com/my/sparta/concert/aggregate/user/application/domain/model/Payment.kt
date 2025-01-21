package com.my.sparta.concert.aggregate.user.application.domain.model

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PayingTransaction
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import lombok.Getter

@Getter
class Payment(
    val paymentId: String,
    val userId: String,
    val paymentType: PaymentType,
    val amount: Double,
    val transaction: PayingTransaction,
)
