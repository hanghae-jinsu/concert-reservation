package com.my.sparta.concert.aggregate.user.application.domain.service

import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserUseMoneyUseCase
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserUseMoneyCommand
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
class UserUseMoneyService(
    private val saveMoneyPort: SaveMoneyPort,
    private val loadUserInfoPort: LoadUserInfoPort,
    private val lockManager: LockManager
) : UserUseMoneyUseCase {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun useMoney(command: UserUseMoneyCommand): Users {

        val lockKey = command.userId + command.paidAmount.toString();

        return lockManager.withLock(lockKey, 0, TimeUnit.SECONDS, lockManager) {
            val userInfo = loadUserInfoPort.getUserInfoById(command.userId)

            userInfo.wallet.useMoney(command.paidAmount)

            logger.info("User ${command.userId} wallet balance: ${userInfo.wallet.money}")

            val savedUserInfo = saveMoneyPort.saveMoney(userInfo)

            savedUserInfo;
        }
    }

    @Transactional
    override fun useMoneyNoneReentrantLock(command: UserUseMoneyCommand): Users {

        val userInfo = loadUserInfoPort.getUserInfoById(command.userId)

        userInfo.wallet.useMoney(command.paidAmount)

        logger.info("useMoneyNoneReentrantLock user ${command.userId} wallet balance: ${userInfo.wallet.money}")

        val savedUserInfo = saveMoneyPort.saveMoney(userInfo)

        val findUserInfo = loadUserInfoPort.getUserInfoById(command.userId)

        logger.info("saved useMoneyNoneReentrantLock user ${findUserInfo.userId} wallet balance: ${findUserInfo.wallet.money}")

        return savedUserInfo;
    }
}
