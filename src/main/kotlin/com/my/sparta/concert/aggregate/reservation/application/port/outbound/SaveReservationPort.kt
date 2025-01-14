package com.my.sparta.concert.aggregate.reservation.application.port.outbound

import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation

interface SaveReservationPort {
    fun saveReservationHistory(reservation: Reservation): Reservation
}
