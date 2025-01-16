package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertSeatEntity
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ConcertSeatJpaRepository : JpaRepository<ConcertSeatEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
        """
    select cs 
    from ConcertSeatEntity as cs 
    where cs.concertSeatId IN :seatId 
      and cs.concertSchedule.concertScheduleId = :scheduleId
""",
    )
    fun findByIdAndScheduleId(
        @Param("seatId") seatId: Int,
        @Param("scheduleId") scheduleId: String,
    ): Optional<ConcertSeatEntity>

}
