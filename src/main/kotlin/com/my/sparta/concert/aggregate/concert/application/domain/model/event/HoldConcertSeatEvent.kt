package com.my.sparta.concert.aggregate.concert.application.domain.model.event

data class HoldConcertSeatEvent(
    val seatId: Int,
    val userId: String
)
