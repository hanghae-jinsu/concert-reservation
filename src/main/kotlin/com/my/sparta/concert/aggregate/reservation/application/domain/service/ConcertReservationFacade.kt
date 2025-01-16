package com.my.sparta.concert.aggregate.reservation.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.port.inbound.SaveConcertInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.ReserveConcertUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SavePaymentInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SaveReservationUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.BuyIngTicketUserUseCase
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class ConcertReservationFacade(
    private val loadConcertPort: LoadConcertPort,
    private val saveConcertInfoUseCase: SaveConcertInfoUseCase,
    private val buyIngTicketUserUseCase: BuyIngTicketUserUseCase,
    private val savePaymentInfoUseCase: SavePaymentInfoUseCase,
    private val saveReservationUseCase: SaveReservationUseCase
) : ReserveConcertUseCase {

    @Transactional
    override fun reserve(command: ConcertReservationCommand): Reservation {

        val concert = loadConcertPort.getConcertInfoById(command.concertId)

        val savedConcertSeat = saveConcertInfoUseCase.saveConcertSeat(command);

        val userInfo = buyIngTicketUserUseCase.saveUser(command, concert);

        val paymentInfo = savePaymentInfoUseCase.savePayment(userInfo, concert, command);

        val reservation = Reservation.createReservation(concert, userInfo, savedConcertSeat, command, paymentInfo)

        return saveReservationUseCase.saveConcertTicket(reservation);

    }
}
