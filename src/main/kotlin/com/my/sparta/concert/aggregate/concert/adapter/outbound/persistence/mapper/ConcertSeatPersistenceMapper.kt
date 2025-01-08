package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertScheduleEntity
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertSeatEntity
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import org.springframework.stereotype.Component

@Component
class ConcertSeatPersistenceMapper {

    fun mapToDomain(entity: ConcertSeatEntity): ConcertSeat {
        return ConcertSeat(
            entity.userId,
            entity.concertScheduleId,
            entity.concertSeatId
        )
    }

    fun mapToEntity(domain: ConcertSeat): ConcertSeatEntity {
        return ConcertSeatEntity(
            domain.id,
            domain.userId,
            domain.concertScheduleId
        )
    }

}
