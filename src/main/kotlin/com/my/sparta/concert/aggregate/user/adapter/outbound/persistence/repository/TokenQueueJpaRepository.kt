package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TokenQueueJpaRepository : JpaRepository<UserTokenEntity, String>
