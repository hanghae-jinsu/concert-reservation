package com.my.sparta.concert.aggregate.user.application.domain.service

import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.port.inbound.GenerateTokenUseCase
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveUserTokenPort
import com.my.sparta.concert.common.util.TokenUtilService
import io.kotest.assertions.any
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GenerateTokenServiceTest {
    private val loadUserInfoPort: LoadUserInfoPort = mockk()
    private val saveUserTokenPort: SaveUserTokenPort = mockk()
    private val tokenUtilService: TokenUtilService = mockk()
    private val service: GenerateTokenUseCase =
        GenerateTokenService(
            loadUserInfoPort = loadUserInfoPort,
            saveUserTokenPort = saveUserTokenPort,
            tokenUtilService = tokenUtilService,
        )

    @Test
    fun `generateToken should load user info, generate token, and save it`() {
        // Arrange
        val userId = "user123"
        val userInfo = Users(userId = userId, "username", 10, mockk())
        val generatedToken = "generatedToken123"
        val savedTokenId = "savedTokenId123"
        every { loadUserInfoPort.getUserInfoById(userId) } returns userInfo
        every { tokenUtilService.generateToken(userId) } returns
            UserToken(
                tokenId = generatedToken,
                userId = userId,
                isActive = false,
                createdAt = LocalDateTime.now(),
                expiresAt = LocalDateTime.now().plusMinutes(5),
            )
        every { saveUserTokenPort.saveUserToken(any()) } returns savedTokenId

        // Act
        val result = service.generateToken(userId)

        // Assert
        assertEquals(savedTokenId, result)
    }
}
