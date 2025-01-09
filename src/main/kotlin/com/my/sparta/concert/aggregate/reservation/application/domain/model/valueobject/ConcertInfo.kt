package com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject

class ConcertInfo(

    var concertId: String,
    var concertName: String,
    var seatInfo: SeatInfo,
    var runningTime: Int,

)
