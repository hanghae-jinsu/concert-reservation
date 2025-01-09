package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.entity.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReservationJpaRepository : JpaRepository<ReservationEntity, UUID> {

}
