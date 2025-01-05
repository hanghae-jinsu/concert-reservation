package com.my.sparta.concert.aggregate.concert.application.domain.model

import java.time.LocalDateTime

class Concert(
    var concertId: String,
    var concertName: String,
    var runningTime: Int,
    var targetAge: Int,
    var notice: String,
)
