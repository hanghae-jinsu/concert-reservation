package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import java.time.LocalDateTime
import java.util.*

@Getter
@Entity
@Table(name = "concert_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ConcertScheduleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "concert_schedule_id", nullable = false)
    var concertScheduleId: String,
    @Column(name = "concert_name")
    var concertName: String,
    @Column(name = "start_date_time")
    var startDateTime: LocalDateTime,
    @Column(name = "end_date_time")
    var endDateTime: LocalDateTime,
    @Column(name = "running_time")
    var runningTime: Int,
    @Column(name = "notice")
    var notice: String,

    @Column(name = "concert_id")
    var concertId: String,
    @Column(name = "concert_hall_id")
    var concertHallId: String,

    @OneToMany(mappedBy = "concertSchedule", cascade = [CascadeType.ALL], orphanRemoval = true)
    val concertSeat: MutableList<ConcertSeatEntity>,

    var finished: Boolean,
) {

    constructor(concertScheduleId: String) : this(
        concertScheduleId = concertScheduleId,
        concertName = "",
        startDateTime = LocalDateTime.now(),
        endDateTime = LocalDateTime.now(),
        runningTime = 0,
        notice = "",
        concertId = "",
        concertHallId = "",
        concertSeat = mutableListOf(),
        finished = false
    )
}
