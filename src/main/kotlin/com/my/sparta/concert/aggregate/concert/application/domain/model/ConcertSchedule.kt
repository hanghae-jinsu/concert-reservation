package com.my.sparta.concert.aggregate.concert.application.domain.model

import lombok.Getter
import java.time.LocalDateTime

@Getter
class ConcertSchedule(
    var concertScheduleId: String,
    var concertName: String,
    var startDateTime: LocalDateTime,
    var endDateTime: LocalDateTime,
    var runningTime: Int,
    var notice: String,
)
