package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, String>
