package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertScheduleEntity
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ConcertScheduleJpaRepository : JpaRepository<ConcertScheduleEntity, UUID> {
    @Query(
        "select t from ConcertScheduleEntity t join fetch t.concertSeat as cs where t.concertId = :concertId and t.finished" +
            " != true and cs.seatStatus = :seatStatus",
    )
    fun findByConcertId(
        concertId: String,
        seatStatus: ConcertSeat.SeatStatus,
    ): List<ConcertScheduleEntity>
}
