package com.my.sparta.concert.aggregate.reservation.application.domain.model.event

data class ExecuteDeleteTokenEvent(
    val tokenValue: String,
)
