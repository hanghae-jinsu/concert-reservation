package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertEntity
import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import org.springframework.stereotype.Component

@Component
class ConcertPersistenceMapper {
    fun mapToDomain(entity: ConcertEntity): Concert {
        return Concert(
            entity.concertId,
            entity.concertName,
            entity.runningTime,
            entity.targetAge,
            entity.notice,
            entity.cost,
        )
    }
}
