package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserTokenEntity
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper.TokenPersistenceMapper
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.TokenQueueJpaRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TokenQueuePersistenceAdapterTest() {

    private val tokenPersistenceMapper: TokenPersistenceMapper = mockk()
    private val tokenQueueJpaRepository: TokenQueueJpaRepository = mockk()
    private val adapter = TokenQueuePersistenceAdapter(tokenPersistenceMapper, tokenQueueJpaRepository)

    @Test
    fun `saveUserToken should map and save token`() {
        // Arrange
        val userToken = UserToken("token1", "user1", false, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10))
        val userTokenEntity = UserTokenEntity("token1", "user1", false, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10))
        every { tokenPersistenceMapper.mapToJpaEntity(userToken) } returns userTokenEntity
        every { tokenQueueJpaRepository.save(userTokenEntity) } returns userTokenEntity

        // Act
        val result = adapter.saveUserToken(userToken)

        // Assert
        assertEquals("token1", result)
    }



    @Test
    fun `loadExpiredTargetTokens should retrieve expired tokens`() {
        // Arrange
        val dateTime = LocalDateTime.of(2025, 1, 9, 21, 3, 20)
        val tokenList = listOf(
            UserTokenEntity("token1", "user1", false, dateTime.minusMinutes(20), dateTime.minusMinutes(10)),
            UserTokenEntity("token2", "user2", false, dateTime.minusMinutes(25), dateTime.minusMinutes(5))
        )
        every { tokenQueueJpaRepository.findByExpiredTargetToken(any()) } returns tokenList

        // Act
        val result = adapter.loadExpiredTargetTokens()

        // Assert
        assertEquals(2, result.size)
        assertEquals("token1", result[0].tokenId)
        assertEquals("token2", result[1].tokenId)
    }


    @Test
    fun `deleteTokens should delete a list of tokens`() {
        // Arrange
        val tokens = listOf(
            UserTokenEntity("token1", "user1", false, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10)),
            UserTokenEntity("token2", "user2", false, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15))
        )
        every { tokenQueueJpaRepository.deleteAll(tokens) } just Runs

        // Act
        adapter.deleteTokens(tokens)

    }
}
