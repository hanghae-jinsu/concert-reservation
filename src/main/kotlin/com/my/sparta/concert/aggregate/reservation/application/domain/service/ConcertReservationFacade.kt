package com.my.sparta.concert.aggregate.reservation.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.ReserveConcertUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.*
import com.my.sparta.concert.aggregate.user.application.domain.model.Payment
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PayingTransaction
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
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
    private val loadConcertSeatPort: LoadConcertSeatPort,
    private val saveConcertSeatPort: SaveConcertSeatPort,
    private val loadUserInfoPort: LoadUserInfoPort,
    private val savePaymentHistoryPort: SavePaymentHistoryPort,
    private val saveReservationPort: SaveReservationPort

) : ReserveConcertUseCase {

    @Transactional
    override fun reserve(command: ConcertReservationCommand): List<Reservation> {

        require(command.concertSeatNumber.size == command.count) {
            "해당 요청은 요청하는 seat size ${command.concertSeatNumber.size} 와  count ${command.count}가 다릅니다."
        }

        // 콘서트 자리 있는지 확인
        loadConcertSeatPort.getConcertSeatDetailInfo(command.concertSeatNumber, command.concertScheduleId)

        val concertSeat = command.concertSeatNumber.map { value ->
            ConcertSeat(command.userId, command.concertScheduleId, value)
        }
        // 콘서트 자리 save 하고 lock 걸기
        saveConcertSeatPort.saveConcertSeat(concertSeat)

        // 결제 트랜잭션 발행.
        val concert = loadConcertPort.getConcertInfoById(command.concertId);
        val userInfo = loadUserInfoPort.getUserInfoById(command.userId);
        // charge 된 유저 정보 가져와서 차감 메서드 실행.

        val totalPrice = concert.cost * command.count;
        userInfo.wallet.useWallet(concert.cost, command.count)

        val payment = Payment("", userInfo.userId, command.paymentType, totalPrice, PayingTransaction.PAYMENT)
        val savePaymentInfo = savePaymentHistoryPort.savePaymentInfo(payment)

        val reservation = concertSeat.map { values ->
            Reservation.createReservation(concert, userInfo, values, command, savePaymentInfo, totalPrice)
        }

        val saveReservationHistory = saveReservationPort.SaveReservationHistory(reservation)
        return saveReservationHistory;
    }
}
