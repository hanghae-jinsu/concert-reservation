package com.my.sparta.concert.aggregate.reservation.application.domain.service

import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.domain.model.event.ChangeStatusUseSeatEvent
import com.my.sparta.concert.aggregate.reservation.application.domain.model.event.ExecuteDeleteTokenEvent
import com.my.sparta.concert.aggregate.reservation.application.domain.model.event.UseUserPointEvent
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SaveReservationUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveReservationPort
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Service
@RequiredArgsConstructor
class SaveConcertReservationService(
    private val saveReservationPort: SaveReservationPort,
    private val eventPublisher: ApplicationEventPublisher,
) : SaveReservationUseCase {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun saveConcertTicket(reservation: Reservation): Reservation {
        val seatEvent =
            ChangeStatusUseSeatEvent(
                reservation.concertInfo.seatInfo.id,
                reservation.buyerInfo.userId,
            )
        var useEvent =
            UseUserPointEvent(
                reservation.buyerInfo.userId,
                reservation.buyerInfo.cost,
            )

        var tokenDeleteEvent = getCurrentRequest().let { ExecuteDeleteTokenEvent(it) }

        eventPublisher.publishEvent(seatEvent)
        eventPublisher.publishEvent(useEvent)
        eventPublisher.publishEvent(tokenDeleteEvent)

        return saveReservationPort.saveReservationHistory(reservation)
    }

    private fun getCurrentRequest(): String {
        val attributes = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        return attributes.request.getHeader("Authorization").also { header ->
            logger.info("Authorization header value: $header")
        }
    }
}
