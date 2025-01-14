package com.my.sparta.concert.aggregate.reservation.application.domain.model

import lombok.Data
import java.time.LocalDateTime

@Data
class SeatLock(
    val seatLockId: String,
    val seatId: Int,
    val holdStartTime: LocalDateTime,
    val holdEndTime: LocalDateTime,
    val userId: String,
)
