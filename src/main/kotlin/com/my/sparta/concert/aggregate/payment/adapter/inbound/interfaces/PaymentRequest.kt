package com.my.sparta.concert.aggregate.payment.adapter.inbound.interfaces

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType

data class PaymentRequest(
    val userId: String,
    val paymentType: PaymentType
)
