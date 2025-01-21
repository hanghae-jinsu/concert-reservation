package com.my.sparta.concert.aggregate.user.application.domain.service

import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserChargeCommand
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveMoneyPort
import com.my.sparta.concert.common.util.LockManager
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UserChargeMoneyServiceTest(
) {

    private val saveMoneyPort: SaveMoneyPort = mockk()
    private val loadUserInfoPort: LoadUserInfoPort = mockk()
    private val lockManager: LockManager = mockk()
    private val service = UserChargeMoneyService(saveMoneyPort, loadUserInfoPort, lockManager)

    @Test
    fun `chargeMoney should load user info, charge money, and save user`() {
        // Arrange
        val userId = "user123"
        val initialMoney = 1000
        val chargeAmount = 1000
        val command =
            UserChargeCommand(
                userId = userId,
                wallet = Wallet(PaymentType.CARD, money = 1000.0),
            )

        val userInfo =
            Users(
                userId = userId,
                username = "Test User",
                age = 12,
                wallet = Wallet(PaymentType.CARD, money = 1000.0),
            )

        val updatedUserInfo =
            Users(
                userId = userId,
                username = "Test User",
                age = 15,
                wallet = Wallet(PaymentType.CARD, money = 2000.0),
            )

        every { loadUserInfoPort.getUserInfoById(userId) } returns userInfo
        every { saveMoneyPort.saveMoney(userInfo) } returns updatedUserInfo

        // Act
        val result = service.chargeMoney(command)

        // Assert
        assertEquals(userId, result.userId)
        assertEquals(initialMoney + chargeAmount, result.wallet.money.toInt())
    }
}
