package com.my.sparta.concert.aggregate.reservation.application.domain.model.event

data class UseUserPointEvent(
    val userId: String,
    val totalPrice: Double,
)
