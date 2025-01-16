package com.my.sparta.concert.aggregate.reservation.application.port.inbound

import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation

interface SaveReservationUseCase {

    fun saveConcertTicket(reservation: Reservation): Reservation
}
