package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper.ReservationPersistenceMapper
import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.repository.ReservationJpaRepository
import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveReservationPort
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class ReservationPersistenceAdapter(
    private val reservationRepository: ReservationJpaRepository,
    private val reservationPersistenceMapper: ReservationPersistenceMapper
) : SaveReservationPort {

    override fun SaveReservationHistory(reservation: Reservation): Reservation {

        val reservationEntity = reservationPersistenceMapper.mapToJpaEntity(reservation);
        val savedEntity = reservationRepository.save(reservationEntity);
        return reservationPersistenceMapper.mapToDomain(savedEntity);
    }
}
