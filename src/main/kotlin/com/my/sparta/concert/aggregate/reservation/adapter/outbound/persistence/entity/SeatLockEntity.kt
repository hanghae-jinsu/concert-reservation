package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Getter
@Entity
@Table(name = "seat_lock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class SeatLockEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "seat_lock_id")
    val seatLockId: String,

    val seatId: Int,

    @Column(name = "start_time")
    val startTime: LocalDateTime,
    @Column(name = "end_time")
    val endTime: LocalDateTime,

    val userId: String

) {
}
