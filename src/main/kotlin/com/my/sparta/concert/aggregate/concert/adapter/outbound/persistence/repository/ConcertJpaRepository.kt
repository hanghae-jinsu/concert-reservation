package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ConcertJpaRepository : JpaRepository<ConcertEntity, String> {

    @Query("select con from ConcertEntity con where con.concertName = :name")
    fun findByConcertName(@Param("name") name: String): Optional<ConcertEntity>
}
