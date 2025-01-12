package com.my.sparta.concert.aggregate.concert.application.domain.model

import lombok.Getter

@Getter
class ConcertSeat(
    var userId: String,
    var concertScheduleId: String,
    var id: Int,
    var isActive: Boolean,
)
