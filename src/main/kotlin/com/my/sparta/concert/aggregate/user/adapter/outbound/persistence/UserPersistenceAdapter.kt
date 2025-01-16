package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper.UserPersistenceMapper
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.UserJpaRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveMoneyPort
import jakarta.persistence.*
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    val userRepository: UserJpaRepository,
    val userPersistenceMapper: UserPersistenceMapper,
) : LoadUserInfoPort, SaveMoneyPort {

    override fun getUserInfoById(userId: String): Users {
        val userInfo =
            userRepository.findById(userId).orElseThrow {
                EntityNotFoundException("해당 $userId 로는 entity를 찾을 수 없습니다.")
            }

        return userPersistenceMapper.mapToDomain(userInfo)
    }

    override fun saveMoney(user: Users): Users {
        val userJpaEntity = userPersistenceMapper.mapToJpaEntity(user)
        val userEntity = userRepository.save(userJpaEntity)
        return userPersistenceMapper.mapToDomain(userEntity)
    }
}
