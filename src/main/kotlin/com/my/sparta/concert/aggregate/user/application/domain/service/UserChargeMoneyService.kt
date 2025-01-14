package com.my.sparta.concert.aggregate.user.application.domain.service

import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserChargeMoneyUseCase
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserChargeCommand
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveMoneyPort
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@RequiredArgsConstructor
class UserChargeMoneyService(
    private val saveMoneyPort: SaveMoneyPort,
    private val loadUserInfoPort: LoadUserInfoPort,
) : UserChargeMoneyUseCase {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun chargeMoney(command: UserChargeCommand): Users {

        val userInfo = loadUserInfoPort.getUserInfoById(command.userId)

        userInfo.wallet.chargeMoney(command.wallet.money.toInt())

        logger.info("info: ${userInfo.wallet.money}")

        val user = saveMoneyPort.saveMoney(userInfo)

        return user
    }
}
