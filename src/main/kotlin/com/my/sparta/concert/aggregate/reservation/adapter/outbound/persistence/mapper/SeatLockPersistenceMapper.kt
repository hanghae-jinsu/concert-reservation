package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.entity.SeatLockEntity
import com.my.sparta.concert.aggregate.reservation.application.domain.model.SeatLock
import org.springframework.stereotype.Component

@Component
class SeatLockPersistenceMapper {

    fun mapToJpaEntity(seatLock: SeatLock): SeatLockEntity {

        return SeatLockEntity(
            seatLockId = "",
            seatId = seatLock.seatId,
            startTime = seatLock.holdStartTime,
            endTime = seatLock.holdEndTime,
            userId = seatLock.userId
        )

    }

    fun mapToEntities(seatLocks: List<SeatLock>): List<SeatLockEntity> {
        return seatLocks.stream().map(this::mapToJpaEntity).toList();
    }

    fun mapToDomainList(seatList: List<SeatLockEntity>): List<SeatLock> {
        return seatList.stream().map(this::mapToDomain).toList();
    }

    fun mapToDomain(entity: SeatLockEntity): SeatLock {
        return SeatLock(
            entity.seatLockId,
            entity.seatId,
            entity.startTime,
            entity.endTime,
            entity.userId
        )
    }

}
