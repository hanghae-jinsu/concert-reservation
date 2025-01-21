package com.my.sparta.concert.aggregate.user.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.port.outbound.BuyIngTicketUserUseCase
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveMoneyPort
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class BuyIngTicketUserService(
    private val loadUserInfoPort: LoadUserInfoPort,
    private val saveMoneyPort: SaveMoneyPort
) : BuyIngTicketUserUseCase {

    @Transactional
    override fun saveUser(command: ConcertReservationCommand, concert: Concert): Users {

        val userInfo = loadUserInfoPort.getUserInfoById(command.userId)
        userInfo.wallet.useWallet(concert.cost, command.count)

        return saveMoneyPort.saveMoney(userInfo)
    }

}
