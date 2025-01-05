package com.my.sparta.concert.aggregate.concert.application.domain.model

import com.my.sparta.concert.aggregate.concert.application.domain.valueobject.HallType

class ConcertHall(
    var hallId: String,
    var hallName: String,
    var hallType: HallType,
    var hallCapacity: Int,
)
