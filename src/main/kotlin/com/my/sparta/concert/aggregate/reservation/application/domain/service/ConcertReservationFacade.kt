package com.my.sparta.concert.aggregate.reservation.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.port.inbound.SaveConcertInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.ReserveConcertUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SavePaymentInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SaveReservationUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class ConcertReservationFacade(
    private val loadConcertPort: LoadConcertPort,
    private val loadUserInfoPort: LoadUserInfoPort,
    private val saveConcertInfoUseCase: SaveConcertInfoUseCase,
    private val savePaymentInfoUseCase: SavePaymentInfoUseCase,
    private val saveReservationUseCase: SaveReservationUseCase,
) : ReserveConcertUseCase {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun reserve(command: ConcertReservationCommand): Reservation {
        val concert = loadConcertPort.getConcertInfoById(command.concertId)
        val userInfo = loadUserInfoPort.getUserInfoById(command.userId)
        val savedConcertSeat = saveConcertInfoUseCase.saveConcertSeat(command)
        val paymentInfo = savePaymentInfoUseCase.savePayment(userInfo, concert, command)
        val reservation = Reservation.createReservation(concert, userInfo, savedConcertSeat, command, paymentInfo)
        return saveReservationUseCase.saveConcertTicket(reservation)
    }
}
