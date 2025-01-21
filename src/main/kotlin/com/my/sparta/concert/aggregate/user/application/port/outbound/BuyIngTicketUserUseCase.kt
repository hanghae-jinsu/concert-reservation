package com.my.sparta.concert.aggregate.user.application.port.outbound

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.user.application.domain.model.Users

interface BuyIngTicketUserUseCase {

    fun saveUser(command: ConcertReservationCommand, concert: Concert): Users

}
