package com.my.sparta.concert.aggregate.reservation.application.port.inbound.command

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType

data class ConcertReservationCommand(
    val concertId: String,
    val userId: String,
    val concertScheduleId: String,
    val concertSeatNumber: Int,
    val count: Int,
    val paymentType: PaymentType,
)
