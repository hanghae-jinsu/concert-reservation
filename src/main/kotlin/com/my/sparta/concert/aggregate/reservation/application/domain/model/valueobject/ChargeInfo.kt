package com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType

class ChargeInfo(

    var paymentId: String,
    var paymentType: PaymentType

)
