package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.entity.ConcertEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ConcertJpaRepository : JpaRepository<ConcertEntity, String> {

}
