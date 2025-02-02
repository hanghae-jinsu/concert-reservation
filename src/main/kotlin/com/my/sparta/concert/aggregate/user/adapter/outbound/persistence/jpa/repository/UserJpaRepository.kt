package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.repository

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, String>
