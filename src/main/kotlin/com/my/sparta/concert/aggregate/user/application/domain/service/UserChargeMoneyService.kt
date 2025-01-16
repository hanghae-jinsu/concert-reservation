package com.my.sparta.concert.aggregate.user.application.domain.service

import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserChargeMoneyUseCase
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserChargeCommand
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveMoneyPort
import com.my.sparta.concert.common.util.LockManager
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Slf4j
@Service
@RequiredArgsConstructor
class UserChargeMoneyService(
    private val saveMoneyPort: SaveMoneyPort,
    private val loadUserInfoPort: LoadUserInfoPort,
    private val lockManager: LockManager
) : UserChargeMoneyUseCase {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun chargeMoney(command: UserChargeCommand): Users {
        val lockKey = command.userId

        return lockManager.withLock(lockKey, 0, TimeUnit.SECONDS, lockManager) {

            val userInfo = loadUserInfoPort.getUserInfoById(command.userId)

            userInfo.wallet.chargeMoney(command.wallet.money.toInt())

            logger.info("User ${command.userId} wallet balance: ${userInfo.wallet.money}")

            val user = saveMoneyPort.saveMoney(userInfo)

            user
        }
    }
}
