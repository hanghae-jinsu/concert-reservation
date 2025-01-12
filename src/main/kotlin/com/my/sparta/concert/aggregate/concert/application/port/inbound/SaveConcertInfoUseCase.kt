package com.my.sparta.concert.aggregate.concert.application.port.inbound

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand

interface SaveConcertInfoUseCase {

    fun saveConcertSeat(command: ConcertReservationCommand): List<ConcertSeat>

}
