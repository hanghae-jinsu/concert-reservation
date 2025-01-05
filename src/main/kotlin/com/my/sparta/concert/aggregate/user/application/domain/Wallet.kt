package com.my.sparta.concert.aggregate.user.application.domain

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType

class Wallet(
    var paymentType: PaymentType,
    var money: Double,
)
