package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor

@Entity
@Getter
@Table(name = "concert_entity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ConcertEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "concert_id")
    var concertId: String,
    @Column(name = "concert_name")
    var concertName: String,
    @Column(name = "running_time")
    var runningTime: Int,
    @Column(name = "target_age")
    var targetAge: Int,
    @Column(name = "notice")
    var notice: String,
    @Column(name = "cost")
    var cost: Double,
) {
    constructor(concertId: String) :
        this(
            concertId = concertId,
            concertName = "",
            runningTime = 0,
            targetAge = 0,
            notice = "",
            cost = 0.0,
        )

    constructor() :
        this(
            concertId = "",
            concertName = "",
            runningTime = 0,
            targetAge = 0,
            notice = "",
            cost = 0.0,
        )
}
