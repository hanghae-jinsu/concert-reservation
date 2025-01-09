package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity

import com.my.sparta.concert.aggregate.concert.application.domain.valueobject.HallType
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@Entity
@Table(name = "concert_hall")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ConcertHallEntity(

    @Id
    @Column(name = "hall_id")
    var hallId: String,

    @Column(name = "hall_name")
    var hallName: String,

    @Enumerated(EnumType.STRING)
    var hallType: HallType,

    @Column(name = "hall_capacity")
    var hallCapacity: Int,
) {
    constructor(hallId: String) : this(
        hallId = hallId,
        hallName = "",
        hallType = HallType.COMMON,
        hallCapacity = 0
    )

    constructor() : this(
        hallId = "",
        hallName = "",
        hallType = HallType.COMMON,
        hallCapacity = 0
    )
}
