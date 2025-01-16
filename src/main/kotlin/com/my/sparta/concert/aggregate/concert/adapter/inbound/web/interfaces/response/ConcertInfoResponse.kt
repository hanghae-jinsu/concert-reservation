package com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import java.time.LocalDateTime


data class ConcertInfoResponse(
    var concertScheduleId: String,
    var concertName: String,
    var startDateTime: LocalDateTime,
    var endDateTime: LocalDateTime,
    var runningTime: Int,
    var concertSeat: List<ConcertSeatResponse>
) {
}
