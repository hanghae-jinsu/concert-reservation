package com.my.sparta.concert.aggregate.integration

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserChargeMoneyUseCase
import com.my.sparta.concert.aggregate.user.application.port.inbound.UserUseMoneyUseCase
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserChargeCommand
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserUseMoneyCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IsolationConcurrencyTest(
    @Autowired private val userUseMoneyUseCase: UserUseMoneyUseCase,
    @Autowired private val userChargeMoneyUseCase: UserChargeMoneyUseCase,
) {
    private lateinit var userChargeCommand: UserChargeCommand
    private lateinit var userUseMoneyCommand: UserUseMoneyCommand

    @BeforeEach
    fun setUp() {
        userChargeCommand =
            UserChargeCommand(
                userId = "user2",
                wallet =
                    Wallet(
                        PaymentType.CARD, BigDecimal(3000),
                    ),
            )
        userUseMoneyCommand =
            UserUseMoneyCommand(
                userId = "user2",
                paidAmount = BigDecimal(3000),
            )
    }

//    @RepeatedTest(100)
//    @DisplayName("기본 isolation에서 thread 2개로 각기다른 클라이언트가 충전, 차감 요청을 한다. 동시성 이슈 테스트")
//    fun `test for default isolation concurrency`() {
//
//        val threadCount = 2
//        val latch = CountDownLatch(threadCount)
//        val executor = Executors.newFixedThreadPool(threadCount)
//
//        val successCount = AtomicInteger(0)
//        val failureCount = AtomicInteger(0)
//        val totalCount = AtomicInteger(0)
//
//        executor.submit {
//            try {
//                userChargeMoneyUseCase.chargeMoneyNoneReentrantLock(userChargeCommand)
//                successCount.incrementAndGet()
//            } catch (e: Exception) {
//                failureCount.incrementAndGet()
//            } finally {
//                latch.countDown()
//            }
//        }
//
//        executor.submit {
//            try {
//                val userInfo = userUseMoneyUseCase.useMoneyNoneReentrantLock(userUseMoneyCommand)
//                totalCount.set(userInfo.wallet.money.toInt())
//                successCount.incrementAndGet()
//            } catch (e: Exception) {
//                failureCount.incrementAndGet()
//            } finally {
//                latch.countDown()
//            }
//        }
//        latch.await()
//        executor.shutdown()
//        executor.awaitTermination(5, TimeUnit.SECONDS)
//
//        assertThat(successCount.get()).isEqualTo(2)
//        assertThat(totalCount.get()).isEqualTo(40000)
//
//    }

    @Test
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @DisplayName("다른 트랜잭션이 커밋한 값을 즉시 읽을 수 있는지 확인")
    fun `test for default isolation READ_COMMITTED`() {
        val threadCount = 2
        val latch = CountDownLatch(threadCount)
        val executor = Executors.newFixedThreadPool(threadCount)

        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        executor.submit {
            try {
                latch.countDown() // 동시 실행을 위해 대기
                latch.await()

                val userInfo = userChargeMoneyUseCase.chargeMoneyNoneReentrantLock(userChargeCommand) // 충전
                successCount.incrementAndGet()
            } catch (e: Exception) {
                failureCount.incrementAndGet()
            }
        }

        executor.submit {
            try {
                latch.countDown()
                latch.await()

                val userInfo = userUseMoneyUseCase.useMoneyNoneReentrantLock(userUseMoneyCommand) // 차감
                successCount.incrementAndGet()
            } catch (e: Exception) {
                failureCount.incrementAndGet()
            }
        }

        executor.shutdown()
        executor.awaitTermination(5, TimeUnit.SECONDS)

        assertThat(successCount.get()).isEqualTo(2)
    }

    @Test
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @DisplayName("같은 데이터를 여러 번 조회해도 동일한 값이 유지되는지 확인")
    fun `test for default isolation REPEATABLE_READ`() {
        val threadCount = 2
        val latch = CountDownLatch(threadCount)
        val executor = Executors.newFixedThreadPool(threadCount)

        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        executor.submit {
            try {
                latch.countDown() // 동시 실행을 위해 대기
                latch.await()

                val userInfo = userChargeMoneyUseCase.chargeMoneyNoneReentrantLock(userChargeCommand) // 충전
                successCount.incrementAndGet()
            } catch (e: Exception) {
                failureCount.incrementAndGet()
            }
        }

        executor.submit {
            try {
                latch.countDown()
                latch.await()

                val userInfo = userUseMoneyUseCase.useMoneyNoneReentrantLock(userUseMoneyCommand)
                successCount.incrementAndGet()
            } catch (e: Exception) {
                failureCount.incrementAndGet()
            }
        }

        executor.shutdown()
        executor.awaitTermination(5, TimeUnit.SECONDS)

        assertThat(successCount.get()).isEqualTo(2)
    }

    @Test
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @DisplayName("하나의 트랜잭션이 끝날 때까지 다른 트랜잭션이 대기하는 것을 확인 ")
    fun `test for default isolation SERIALIZABLE`() {
        val threadCount = 2
        val latch = CountDownLatch(threadCount)
        val executor = Executors.newFixedThreadPool(threadCount)

        val successCount = AtomicInteger(0)
        val failureCount = AtomicInteger(0)

        executor.submit {
            try {
                latch.countDown() // 동시 실행을 위해 대기
                latch.await()

                val userInfo = userChargeMoneyUseCase.chargeMoneyNoneReentrantLock(userChargeCommand) // 충전
                successCount.incrementAndGet()
            } catch (e: Exception) {
                failureCount.incrementAndGet()
            }
        }

        executor.submit {
            try {
                latch.countDown()
                latch.await()

                val userInfo = userUseMoneyUseCase.useMoneyNoneReentrantLock(userUseMoneyCommand) // 차감
                successCount.incrementAndGet()
            } catch (e: Exception) {
                failureCount.incrementAndGet()
            }
        }

        executor.shutdown()
        executor.awaitTermination(5, TimeUnit.SECONDS)

        assertThat(successCount.get()).isEqualTo(2)
    }
}
