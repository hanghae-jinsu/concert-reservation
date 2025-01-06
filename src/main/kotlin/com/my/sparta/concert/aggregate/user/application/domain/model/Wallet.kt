package com.my.sparta.concert.aggregate.user.application.domain.model

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType

class Wallet(
    var paymentType: PaymentType,
    var money: Double,
)
