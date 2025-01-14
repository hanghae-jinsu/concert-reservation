package com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.interfaces

import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@NoArgsConstructor
class ConcertReservationRequest(
    val concertId: String,
    val userId: String,
    val concertScheduleId: String,
    val concertSeatNumber: Int,
    val count: Int,
    val paymentType: String,
)
