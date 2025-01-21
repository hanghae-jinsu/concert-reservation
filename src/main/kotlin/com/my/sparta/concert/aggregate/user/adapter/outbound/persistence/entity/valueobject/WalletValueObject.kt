package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.valueobject

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class WalletValueObject(
    @Enumerated(EnumType.STRING)
    val paymentType: PaymentType,
    val money: Double,
)
