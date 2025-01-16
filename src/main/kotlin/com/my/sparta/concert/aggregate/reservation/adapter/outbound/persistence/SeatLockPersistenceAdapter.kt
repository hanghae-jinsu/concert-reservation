package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.concert.application.port.outbound.SaveSeatLockPort
import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper.SeatLockPersistenceMapper
import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.repository.SeatLockJpaRepository
import com.my.sparta.concert.aggregate.reservation.application.domain.model.SeatLock
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadSeatLockPort
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
@RequiredArgsConstructor
class SeatLockPersistenceAdapter(
    private val seatLockPersistenceMapper: SeatLockPersistenceMapper,
    private val seatLockJpaRepository: SeatLockJpaRepository
) : SaveSeatLockPort, LoadSeatLockPort {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    override fun saveHoldSeatInfo(seatLock: SeatLock) {

        logger.info("log for lock seat info : {}", seatLock)

        val entity = seatLockPersistenceMapper.mapToCreateJpaEntity(seatLock);
        seatLockJpaRepository.save(entity)

    }

    override fun deleteSeatLocks(seatList: List<SeatLock>) {

        logger.info("log deleteSeatLocks : {}", seatList);

        val entities = seatLockPersistenceMapper.mapToEntities(seatList);
        seatLockJpaRepository.deleteAll(entities);
    }

    override fun getSeatLockByExpired(): List<SeatLock> {

        val now = LocalDateTime.now();
        val seatList = seatLockJpaRepository.findByExpiredLockList(now);
        return seatLockPersistenceMapper.mapToDomainList(seatList);
    }
}
