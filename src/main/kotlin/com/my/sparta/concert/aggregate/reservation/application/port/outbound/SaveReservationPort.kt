package com.my.sparta.concert.aggregate.reservation.application.port.outbound

import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation

interface SaveReservationPort {

    fun SaveReservationHistory(reservation: List<Reservation>): List<Reservation>
}
