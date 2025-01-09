package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import java.time.LocalDateTime

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

    @ManyToOne(fetch = FetchType.LAZY)
    var concert: ConcertEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    var concertHall: ConcertHallEntity,

) {


    constructor(concertScheduleId: String) : this(
        concertScheduleId = concertScheduleId,
        concertName = "",
        startDateTime = LocalDateTime.now(),
        endDateTime = LocalDateTime.now(),
        runningTime = 0,
        notice = "",
        concert = ConcertEntity(),
        concertHall = ConcertHallEntity(),
    )


}
