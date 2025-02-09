package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertSeatEntity
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import java.util.*

interface ConcertSeatJpaRepository : JpaRepository<ConcertSeatEntity, Long> {
    @Query(
        """
    select cs 
    from ConcertSeatEntity as cs 
    where cs.concertSeatId IN :seatId 
      and cs.concertSchedule.concertScheduleId = :scheduleId
      and cs.seatStatus in :seatStatus
""",
    )
    @QueryHints(
        QueryHint(
            name = "jakarta.persistence.lock.timeout",
            value = "1500",
        ),
    )
    fun findByIdAndScheduleId(
        @Param("seatId") seatId: Int,
        @Param("scheduleId") scheduleId: String,
        @Param("seatStatus") seatStatus: List<ConcertSeat.SeatStatus>,
    ): Optional<ConcertSeatEntity>

    @Query("select cs from ConcertSeatEntity cs where cs.concertSeatId = :seatId and cs.seatStatus = :status")
    fun findByIdWithStatus(
        seatId: Long,
        status: ConcertSeat.SeatStatus,
    ): ConcertSeatEntity
}
