package com.my.sparta.concert.aggregate.reservation.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.port.inbound.SaveConcertInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.ReserveConcertUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SavePaymentInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveReservationPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.BuyIngTicketUserUseCase
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// Todo; layer 하나 더 만들 것.
@Slf4j
@Service
@RequiredArgsConstructor
class ConcertReservationFacade(
    private val loadConcertPort: LoadConcertPort,
    private val saveConcertInfoUseCase: SaveConcertInfoUseCase,
    private val buyIngTicketUserUseCase: BuyIngTicketUserUseCase,
    private val savePaymentInfoUseCase: SavePaymentInfoUseCase,
    private val saveReservationPort: SaveReservationPort,
) : ReserveConcertUseCase {

    @Transactional
    override fun reserve(command: ConcertReservationCommand): Reservation {

        require(command.concertSeatNumber == command.count) {
            "해당 요청은 요청하는 seat size ${command.concertSeatNumber} 와  count ${command.count}가 다릅니다."
        }

        val concert = loadConcertPort.getConcertInfoById(command.concertId)

        val savedConcertSeat = saveConcertInfoUseCase.saveConcertSeat(command);

        val userInfo = buyIngTicketUserUseCase.saveUser(command, concert);
        val paymentInfo = savePaymentInfoUseCase.savePayment(userInfo, concert, command);

        val reservation =  Reservation.createReservation(concert, userInfo, savedConcertSeat, command, paymentInfo)


        return saveReservationPort.saveReservationHistory(reservation)

    }
}
