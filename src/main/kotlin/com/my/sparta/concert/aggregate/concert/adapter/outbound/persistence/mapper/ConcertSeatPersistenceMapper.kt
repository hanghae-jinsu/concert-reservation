package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertSeatEntity
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import org.springframework.stereotype.Component

@Component
class ConcertSeatPersistenceMapper {

    fun mapToDomain(entity: ConcertSeatEntity): ConcertSeat {
        return ConcertSeat(
            entity.userId,
            entity.concertScheduleId,
            entity.concertSeatId,
            entity.isActive
        )
    }

    fun mapToEntity(domain: ConcertSeat): ConcertSeatEntity {
        return ConcertSeatEntity(
            domain.id,
            domain.userId,
            domain.concertScheduleId,
            domain.isActive
        )
    }

    fun mapToEntities(domain: List<ConcertSeat>): List<ConcertSeatEntity> {
        return domain.stream().map(this::mapToEntity).toList()
    }

    fun mapToDomainList(savedEntities: List<ConcertSeatEntity>): List<ConcertSeat> {
        return savedEntities.stream().map(this::mapToDomain).toList()

    }
}
