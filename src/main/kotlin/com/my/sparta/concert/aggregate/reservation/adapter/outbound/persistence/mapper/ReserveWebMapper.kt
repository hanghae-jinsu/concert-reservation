package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.reservation.adapter.inbound.web.interfaces.ConcertReservationRequest
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import org.springframework.stereotype.Component

@Component
class ReserveWebMapper {
    fun mapToCommand(request: ConcertReservationRequest): ConcertReservationCommand {
        return ConcertReservationCommand(
            request.concertId,
            request.userId,
            request.concertScheduleId,
            request.concertSeatNumber,
            request.count,
            PaymentType.valueOf(request.paymentType),
        )
    }
}
