package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertSeatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ConcertSeatJpaRepository : JpaRepository<ConcertSeatEntity, Long> {
    @Query(
        """
    select cs 
    from ConcertSeatEntity as cs 
    where cs.concertSeatId IN :seatId 
      and cs.concertScheduleId = :scheduleId
""",
    )
    fun findByIdAndScheduleId(
        @Param("seatId") seatId: Int,
        @Param("scheduleId") scheduleId: String,
    ): Optional<ConcertSeatEntity>
}
