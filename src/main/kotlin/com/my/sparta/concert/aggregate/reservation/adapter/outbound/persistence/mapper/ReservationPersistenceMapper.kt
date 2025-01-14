package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.entity.ReservationEntity
import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.BuyerInfo
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.ChargeInfo
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.ConcertInfo
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.SeatInfo
import org.springframework.stereotype.Component

@Component
class ReservationPersistenceMapper {
    fun mapToJpaEntity(reservation: Reservation): ReservationEntity {
        return ReservationEntity(
            reservationId = reservation.reservationId,
            paymentId = reservation.chargeInfo.paymentId,
            paymentType = reservation.chargeInfo.paymentType,
            concertId = reservation.concertInfo.concertId,
            concertName = reservation.concertInfo.concertName,
            seatId = reservation.concertInfo.seatInfo.id,
            runningTime = reservation.concertInfo.runningTime,
            userId = reservation.buyerInfo.userId,
            cost = reservation.buyerInfo.cost,
            count = reservation.buyerInfo.count,
            targetAge = reservation.buyerInfo.targetAge,
        )
    }

    fun mapToDomain(entity: ReservationEntity): Reservation {
        return Reservation(
            entity.reservationId,
            chargeInfo = ChargeInfo(entity.paymentId, entity.paymentType),
            concertInfo =
                ConcertInfo(
                    entity.concertId,
                    entity.concertName,
                    SeatInfo(entity.seatId),
                    entity.runningTime,
                ),
            buyerInfo = BuyerInfo(entity.userId, entity.cost, entity.count, entity.targetAge),
        )
    }

    fun mapToJpaEntities(reservations: List<Reservation>): List<ReservationEntity> {
        return reservations.stream().map(this::mapToJpaEntity).toList()
    }

    fun mapToDomainList(savedEntity: List<ReservationEntity>): List<Reservation> {
        return savedEntity.stream().map(this::mapToDomain).toList()
    }
}
