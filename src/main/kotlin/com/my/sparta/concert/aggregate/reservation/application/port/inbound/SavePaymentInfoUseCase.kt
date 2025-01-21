package com.my.sparta.concert.aggregate.reservation.application.port.inbound

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.user.application.domain.model.Payment
import com.my.sparta.concert.aggregate.user.application.domain.model.Users

interface SavePaymentInfoUseCase {

    fun savePayment(userInfo: Users, concert: Concert, command: ConcertReservationCommand): Payment

}
