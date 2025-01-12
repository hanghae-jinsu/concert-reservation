package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.entity

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ReservationEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id")
    val reservationId: String,
    var paymentId: String,
    @Enumerated(EnumType.STRING)
    var paymentType: PaymentType,
    var concertId: String,
    var concertName: String,
    var seatId: Int,
    var runningTime: Int,
    var userId: String,
    var cost: Double,
    var count: Int,
    var targetAge: Int,
)
