package com.my.sparta.concert.aggregate.reservation.application.domain.model

import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.BuyerInfo
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.ChargeInfo
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.ConcertInfo

class Reservation(
    var reservationId: String,
    var chargeInfo: ChargeInfo,
    var concertInfo: ConcertInfo,
    var buyerInfo: BuyerInfo
)
