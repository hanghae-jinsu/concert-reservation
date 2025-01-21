package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertScheduleEntity
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertSeatEntity
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSchedule
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import org.springframework.stereotype.Component

@Component
class ConcertSchedulePersistenceMapper {

    fun mapToDomainList(concertScheduleList: List<ConcertScheduleEntity>): List<ConcertSchedule> {
        return concertScheduleList.stream().map(this::mapToDomain).toList()
    }

    private fun mapToDomain(entity: ConcertScheduleEntity): ConcertSchedule {
        return ConcertSchedule(
            entity.concertScheduleId,
            entity.concertName,
            entity.startDateTime,
            entity.endDateTime,
            entity.runningTime,
            entity.notice,
            entity.concertId,
            entity.concertHallId,
            concertSeat = entity.concertSeat.map { seatEntity ->
                mapConcertSeatToDomain(seatEntity)
            }
        )
    }

    private fun mapConcertSeatToDomain(seatEntity: ConcertSeatEntity): ConcertSeat {
        return ConcertSeat(
            id = seatEntity.concertSeatId,
            userId = seatEntity.userId,
            concertScheduleId = seatEntity.concertSchedule.concertScheduleId,
            seatStatus = seatEntity.seatStatus
        )
    }

    private fun mapToSeatList(concertSeat: MutableList<ConcertSeatEntity>): List<ConcertSeat> {
        return concertSeat.stream().map(this::mapToSeat).toList()
    }

    private fun mapToSeat(entity: ConcertSeatEntity): ConcertSeat {
        return ConcertSeat(
            entity.concertSeatId,
            entity.userId,
            entity.concertSchedule.concertScheduleId,
            entity.seatStatus
        )
    }
}
