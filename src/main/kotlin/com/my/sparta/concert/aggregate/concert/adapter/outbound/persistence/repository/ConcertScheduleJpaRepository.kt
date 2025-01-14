package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ConcertScheduleJpaRepository : JpaRepository<ConcertScheduleEntity, UUID> {

    @Query("select t from ConcertScheduleEntity t where t.concert.concertId = :concertId and t.finished != true")
    fun findByConcertId(concertId: String): List<ConcertScheduleEntity>
}
