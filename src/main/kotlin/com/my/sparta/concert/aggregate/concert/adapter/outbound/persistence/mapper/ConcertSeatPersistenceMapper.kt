package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertScheduleEntity
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertSeatEntity
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import org.springframework.stereotype.Component

@Component
class ConcertSeatPersistenceMapper {

    fun mapToDomain(entity: ConcertSeatEntity): ConcertSeat {
        return ConcertSeat(
            entity.concertSeatId,
            entity.userId,
            entity.concertSchedule.concertScheduleId,
            entity.seatStatus
        )
    }

    fun mapToEntity(domain: ConcertSeat): ConcertSeatEntity {
        return ConcertSeatEntity(
            domain.id,
            domain.userId,
            domain.seatStatus,
            ConcertScheduleEntity(
                domain.concertScheduleId
            )
        )
    }

    fun mapToEntities(domain: List<ConcertSeat>): List<ConcertSeatEntity> {
        return domain.stream().map(this::mapToEntity).toList()
    }

    fun mapToDomainList(savedEntities: List<ConcertSeatEntity>): List<ConcertSeat> {
        return savedEntities.stream().map(this::mapToDomain).toList()

    }
}
