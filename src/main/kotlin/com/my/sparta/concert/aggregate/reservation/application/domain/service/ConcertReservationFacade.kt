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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class ConcertReservationFacade(
    private val loadConcertPort: LoadConcertPort,
    private val saveConcertInfoUseCase: SaveConcertInfoUseCase,
    private val buyIngTicketUserUseCase: BuyIngTicketUserUseCase,
    private val savePaymentInfoUseCase: SavePaymentInfoUseCase,
    private val saveReservationUseCase: SaveReservationUseCase
) : ReserveConcertUseCase {

    // lock 을 건게 순서를 보장할 순 없다. ..
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun reserve(command: ConcertReservationCommand): Reservation {

        val concert = loadConcertPort.getConcertInfoById(command.concertId) // 1
        logger.info("[ reserve ] :  1")
        val userInfo = buyIngTicketUserUseCase.saveUser(command, concert); //2
        logger.info("[ reserve ] :  2")
        val paymentInfo = savePaymentInfoUseCase.savePayment(userInfo, concert, command); //3
        logger.info("[ reserve ] :  3")
        val savedConcertSeat = saveConcertInfoUseCase.saveConcertSeat(command); // 4
        logger.info("[ reserve ] :  4")
        val reservation = Reservation.createReservation(concert, userInfo, savedConcertSeat, command, paymentInfo)
        logger.info("[ reserve ] :  5")
        return saveReservationUseCase.saveConcertTicket(reservation); // 5

    }
}
