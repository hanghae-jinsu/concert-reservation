package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertEntity
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertHallEntity
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertScheduleEntity
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper.ConcertSchedulePersistenceMapper
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository.ConcertScheduleJpaRepository
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSchedule
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConcertScheduleSchedulePersistenceAdapterTest {

    private val concertScheduleRepository: ConcertScheduleJpaRepository = mockk()
    private val concertSchedulePersistenceMapper: ConcertSchedulePersistenceMapper = mockk()
    private val adapter =
        ConcertScheduleSchedulePersistenceAdapter(concertScheduleRepository, concertSchedulePersistenceMapper)

    @Test
    fun `getConcertScheduleById should return mapped concert schedules with relationships`() {
        // Arrange
        val concertId = "concert123"
        val concertEntity = ConcertEntity(concertId = concertId)
        val concertHallEntity = ConcertHallEntity(hallId = "hall1")

        val concertScheduleEntities =
            listOf(
                ConcertScheduleEntity(
                    concertScheduleId = "schedule1",
                    concertName = "Amazing Concert",
                    startDateTime = LocalDateTime.of(2025, 1, 10, 18, 0),
                    endDateTime = LocalDateTime.of(2025, 1, 10, 20, 0),
                    runningTime = 120,
                    notice = "Please arrive 30 minutes early.",
                    concert = concertEntity,
                    concertHall = concertHallEntity,
                    finished = false
                ),
                ConcertScheduleEntity(
                    concertScheduleId = "schedule2",
                    concertName = "Amazing Concert",
                    startDateTime = LocalDateTime.of(2025, 1, 11, 18, 0),
                    endDateTime = LocalDateTime.of(2025, 1, 11, 20, 0),
                    runningTime = 120,
                    notice = "Please bring your ticket.",
                    concert = concertEntity,
                    concertHall = concertHallEntity,
                    finished = false
                ),
            )

        val concertSchedules =
            listOf(
                ConcertSchedule(
                    concertScheduleId = "schedule1",
                    concertName = "Amazing Concert",
                    startDateTime = LocalDateTime.of(2025, 1, 10, 18, 0),
                    endDateTime = LocalDateTime.of(2025, 1, 10, 20, 0),
                    runningTime = 120,
                    notice = "Please arrive 30 minutes early.",
                ),
                ConcertSchedule(
                    concertScheduleId = "schedule2",
                    concertName = "Amazing Concert",
                    startDateTime = LocalDateTime.of(2025, 1, 11, 18, 0),
                    endDateTime = LocalDateTime.of(2025, 1, 11, 20, 0),
                    runningTime = 120,
                    notice = "Please bring your ticket.",
                ),
            )

        every { concertScheduleRepository.findByConcertId(concertId) } returns concertScheduleEntities
        every { concertSchedulePersistenceMapper.mapToDomainList(concertScheduleEntities) } returns concertSchedules

        // Act
        val result = adapter.getConcertScheduleById(concertId)

        // Assert
        assertEquals(2, result.size)
        assertEquals("schedule1", result[0].concertScheduleId)
        assertEquals("schedule2", result[1].concertScheduleId)
    }
}
