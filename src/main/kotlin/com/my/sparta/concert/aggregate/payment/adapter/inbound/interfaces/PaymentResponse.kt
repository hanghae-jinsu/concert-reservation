package com.my.sparta.concert.aggregate.payment.adapter.inbound.interfaces

data class PaymentResponse(

    val httpStatus: Int = 200,
    val message: String

)
