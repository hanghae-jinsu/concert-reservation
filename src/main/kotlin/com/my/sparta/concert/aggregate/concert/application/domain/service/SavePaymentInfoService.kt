package com.my.sparta.concert.aggregate.concert.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SavePaymentInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SavePaymentHistoryPort
import com.my.sparta.concert.aggregate.user.application.domain.model.Payment
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PayingTransaction
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class SavePaymentInfoService(
    private val savePaymentHistoryPort: SavePaymentHistoryPort,
) : SavePaymentInfoUseCase {

    @Transactional
    override fun savePayment(userInfo: Users, concert: Concert, command: ConcertReservationCommand): Payment {

        val totalPrice = concert.cost * command.count
        userInfo.wallet.useWallet(concert.cost, command.count)

        val payment = Payment("", userInfo.userId, command.paymentType, totalPrice, PayingTransaction.PAYMENT)
        return savePaymentHistoryPort.savePaymentInfo(payment)

    }
}
