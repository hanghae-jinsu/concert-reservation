package com.my.sparta.concert.aggregate.reservation.application.domain.model.event

data class ChangeStatusUseSeatEvent(
    val seatId: Int,
    val userId: String
) {
}
