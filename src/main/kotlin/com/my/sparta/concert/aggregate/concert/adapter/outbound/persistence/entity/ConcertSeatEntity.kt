package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor

@Entity
@Getter
@Table(name = "concert_seats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ConcertSeatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_seat_id", nullable = false, unique = true)
    val concertSeatId: Int,
    val userId: String,
    @Column(name = "concert_schedule_id")
    val concertScheduleId: String,
)
