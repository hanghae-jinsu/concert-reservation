package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper.ReservationPersistenceMapper
import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.repository.ReservationJpaRepository
import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveReservationPort
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@RequiredArgsConstructor
class ReservationPersistenceAdapter(
    private val reservationRepository: ReservationJpaRepository,
    private val reservationPersistenceMapper: ReservationPersistenceMapper,
) : SaveReservationPort {

    @Transactional
    override fun saveReservationHistory(reservations: List<Reservation>): List<Reservation> {
        val reservationEntity = reservationPersistenceMapper.mapToJpaEntities(reservations)
        val savedEntity = reservationRepository.saveAll(reservationEntity)
        return reservationPersistenceMapper.mapToDomainList(savedEntity)
    }
}
