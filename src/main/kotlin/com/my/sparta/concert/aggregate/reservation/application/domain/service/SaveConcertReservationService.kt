package com.my.sparta.concert.aggregate.reservation.application.domain.service

import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.domain.model.event.ChangeStatusUseSeatEvent
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SaveReservationUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveReservationPort
import lombok.RequiredArgsConstructor
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class SaveConcertReservationService(
    private val saveReservationPort: SaveReservationPort,
    private val eventPublisher: ApplicationEventPublisher,
) : SaveReservationUseCase {

    @Transactional
    override fun saveConcertTicket(reservation: Reservation): Reservation {

        val event = ChangeStatusUseSeatEvent(
            reservation.concertInfo.seatInfo.id,
            reservation.buyerInfo.userId
        )
        eventPublisher.publishEvent(event);

        return saveReservationPort.saveReservationHistory(reservation);
    }
}
