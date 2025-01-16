package com.my.sparta.concert.aggregate.integration

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserChargeMoneyUseCase
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserChargeCommand
import com.my.sparta.concert.common.util.LockManager.LockAcquisitionException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChargeMoneyConcurrencyTest(
    @Autowired private val userChargeMoneyUseCase: UserChargeMoneyUseCase
) {

    @Test
    fun `동일 사용자 중복 요청 테스트`() {

        val command = UserChargeCommand(userId = "user1", wallet = Wallet(PaymentType.CARD, money = 1000.0))

        val executor = Executors.newFixedThreadPool(3)

        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        (1..3).forEach {
            executor.submit {
                try {
                    userChargeMoneyUseCase.chargeMoney(command)
                    successCount.incrementAndGet()
                    println("충전 성공")
                } catch (e: LockAcquisitionException) {
                    println("중복 요청 차단: ${e.message}")
                    failureCount.incrementAndGet()
                }
            }
        }

        executor.shutdown()
        executor.awaitTermination(5, TimeUnit.SECONDS)

        assertEquals(1, successCount.get())
        assertEquals(2, failureCount.get())

    }
}
