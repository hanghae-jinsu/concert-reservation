package com.my.sparta.concert.aggregate.integration

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserChargeMoneyUseCase
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserChargeCommand
import com.my.sparta.concert.common.util.LockManager.LockAcquisitionException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChargeMoneyConcurrencyTest(
    @Autowired private val userChargeMoneyUseCase: UserChargeMoneyUseCase,
) {

    lateinit var chargeCommand: MutableList<UserChargeCommand>
    lateinit var nonChargeCommand: MutableList<UserChargeCommand>

    @BeforeEach
    fun setUp() {
        chargeCommand = mutableListOf() // 초기화
        nonChargeCommand = mutableListOf() // 초기화

        (0..9).forEach { i ->
            chargeCommand.add(
                i,
                UserChargeCommand(
                    userId = "user5",
                    wallet = Wallet(PaymentType.CARD, BigDecimal(5000.0)),
                ),
            )
        }

        (0..9).forEach { i ->
            nonChargeCommand.add(
                i,
                UserChargeCommand(
                    userId = "user5",
                    wallet = Wallet(PaymentType.CARD, BigDecimal(5000.0)),
                ),
            )
        }
    }

    @Test
    fun `동일 사용자 중복 요청 ReentrantLock 적용 테스트`() {
        val executor = Executors.newFixedThreadPool(3)

        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        (0..2).forEach { i ->
            executor.submit {
                try {
                    userChargeMoneyUseCase.chargeMoney(chargeCommand[i])
                    successCount.incrementAndGet()
                    println("충전 성공")
                } catch (e: LockAcquisitionException) {
                    println("중복 요청 차단: ${e.message}")
                    failureCount.incrementAndGet()
                }
            }
        }

        executor.shutdown()
        executor.awaitTermination(2, TimeUnit.SECONDS)

        assertEquals(1, successCount.get())
        assertEquals(2, failureCount.get())
    }

    @Test
    fun `동일 사용자 중복 요청 ReentrantLock 미적용 테스트`() {
        val executor = Executors.newFixedThreadPool(3)

        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        (0..2).forEach { i ->
            executor.submit {
                try {
                    userChargeMoneyUseCase.chargeMoneyNoneReentrantLock(chargeCommand[i])
                    successCount.incrementAndGet()
                    println("충전 성공")
                } catch (e: LockAcquisitionException) {
                    println("중복 요청 차단: ${e.message}")
                    failureCount.incrementAndGet()
                }
            }
        }

        executor.shutdown()
        executor.awaitTermination(2, TimeUnit.SECONDS)

        assertTrue(successCount.get() > 1, "성공요청이 1개 보다 많다.")
    }
}
