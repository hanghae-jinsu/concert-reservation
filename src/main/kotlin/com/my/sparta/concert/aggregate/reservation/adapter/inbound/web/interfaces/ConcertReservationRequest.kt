package com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.interfaces

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@NoArgsConstructor
class ConcertReservationRequest(

    val concertId: String,
    val userId: String,
    val concertScheduleId: String,
    val concertSeatNumber: List<Int>,
    val count: Int,
    val paymentType: String,

) {

}
