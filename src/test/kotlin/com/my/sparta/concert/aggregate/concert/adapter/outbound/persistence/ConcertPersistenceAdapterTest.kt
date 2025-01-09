package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertEntity
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper.ConcertPersistenceMapper
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository.ConcertJpaRepository
import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import io.mockk.every
import io.mockk.mockk
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConcertPersistenceAdapterTest {

    private val concertRepository: ConcertJpaRepository = mockk()
    private val concertPersistenceMapper: ConcertPersistenceMapper = mockk()
    private val adapter = ConcertPersistenceAdapter(concertRepository, concertPersistenceMapper)

    @Test
    fun `getConcertInfoById should return concert info when concert exists`() {
        // Arrange
        val concertId = "concert123"
        val concertEntity = ConcertEntity(
            concertId = concertId,

        )
        val concert = Concert(
            concertId = concertId,
            concertName = "Amazing Concert",
            runningTime = 100,
            targetAge = 10,
            notice = "",
            cost = 1000.0
        )

        every { concertRepository.findById(concertId) } returns java.util.Optional.of(concertEntity)
        every { concertPersistenceMapper.mapToDomain(concertEntity) } returns concert

        // Act
        val result = adapter.getConcertInfoById(concertId)

        // Assert
        assertEquals(concert, result)
    }

    @Test
    fun `getConcertInfoById should throw EntityNotFoundException when concert does not exist`() {
        // Arrange
        val concertId = "invalidConcertId"

        every { concertRepository.findById(concertId) } returns java.util.Optional.empty()

        // Act & Assert
        val exception = assertThrows(EntityNotFoundException::class.java) {
            adapter.getConcertInfoById(concertId)
        }
        assertEquals("해당 $concertId 로는 entity를 찾을 수 없습니다.", exception.message)
    }

}

