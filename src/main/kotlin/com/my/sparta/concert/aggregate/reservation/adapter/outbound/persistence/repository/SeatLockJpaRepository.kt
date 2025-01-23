package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.entity.SeatLockEntity
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.*


interface SeatLockJpaRepository : JpaRepository<SeatLockEntity, String> {

    @Query("select e from SeatLockEntity e where e.endTime < :currentTime")
    fun findByExpiredLockList(@Param("currentTime") currentTime: LocalDateTime): List<SeatLockEntity>

    @Query("select e from SeatLockEntity e where e.endTime > :currentTime and e.seatId = :seatId")
    fun findBySeatIdWithCurrentTime(seatId: Int, currentTime: LocalDateTime): Optional<SeatLockEntity>

}
