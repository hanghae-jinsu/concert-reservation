package com.my.sparta.concert.aggregate.user.adapter.inbound.event.listener

import com.my.sparta.concert.aggregate.reservation.application.domain.model.event.UseUserPointEvent
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveMoneyPort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.math.BigDecimal

@Component
class UseUsersPointEventListener(
    private val loadUserInfoPort: LoadUserInfoPort,
    private val saveMoneyPort: SaveMoneyPort,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun useUserPoint(event: UseUserPointEvent) {
        logger.info("돈 나간다!")

        val userInfo = loadUserInfoPort.getUserInfoById(event.userId)
        userInfo.wallet.useMoney(BigDecimal(event.totalPrice))
        saveMoneyPort.saveMoney(userInfo)
    }
}
