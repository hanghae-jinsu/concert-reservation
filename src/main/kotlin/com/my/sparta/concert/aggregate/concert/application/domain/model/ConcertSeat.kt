package com.my.sparta.concert.aggregate.concert.application.domain.model

import lombok.Getter

@Getter
class ConcertSeat(
    var id: Int,
    var userId: String,
    var concertScheduleId: String,
    var seatStatus: SeatStatus
) {

    fun statusUpdate() {
        this.seatStatus = SeatStatus.AVAILABLE
    }

    enum class SeatStatus {
        AVAILABLE, HOLD, RESERVED
    }
}
