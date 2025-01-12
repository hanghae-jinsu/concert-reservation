package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserTokenEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface TokenQueueJpaRepository : JpaRepository<UserTokenEntity, String> {
    @Query(
        """
    select ut 
    from UserTokenEntity as ut 
    where ut.expiresAt >= :time and ut.isActive = false 
    order by ut.createdAt asc
    """,
    )
    fun findByTokenNonExpired(
        @Param("time") time: LocalDateTime,
        pageable: Pageable,
    ): List<UserTokenEntity>

    @Query("select t from UserTokenEntity as t where t.expiresAt < :time and t.isActive = true ")
    fun findByExpiredTargetToken(
        @Param("time") time: LocalDateTime,
    ): List<UserTokenEntity>
}
