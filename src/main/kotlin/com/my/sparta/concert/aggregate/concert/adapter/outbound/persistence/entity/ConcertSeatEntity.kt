package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat.SeatStatus
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

    @Enumerated(EnumType.STRING)
    var seatStatus: SeatStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_schedule_id", nullable = false)
    val concertSchedule: ConcertScheduleEntity

)
