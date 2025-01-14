package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.entity.SeatLockEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface SeatLockJpaRepository : JpaRepository<SeatLockEntity, String> {

    @Query("select e from SeatLockEntity e where e.endTime < :currentTime")
    fun findByExpiredLockList(@Param("currentTime") currentTime: LocalDateTime): List<SeatLockEntity>

}
