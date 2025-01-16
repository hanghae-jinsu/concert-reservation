package com.my.sparta.concert.aggregate.reservation.application.port.inbound

import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand

interface ReserveConcertUseCase {
    fun reserve(concertReservationCommand: ConcertReservationCommand)
}
