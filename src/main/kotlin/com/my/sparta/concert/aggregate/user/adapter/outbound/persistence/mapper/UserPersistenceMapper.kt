package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserEntity
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.valueobject.WalletValueObject
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import org.springframework.stereotype.Component

@Component
class UserPersistenceMapper {

    fun mapToDomain(userInfo: UserEntity): Users {
        return Users(
            userInfo.userId,
            userInfo.userName,
            userInfo.age,
            wallet = Wallet(
                userInfo.walletValueObject.paymentType,
                userInfo.walletValueObject.money,
            ),
        )
    }

    fun mapToJpaEntity(user: Users): UserEntity {
        return UserEntity(
            user.userId,
            user.username,
            user.age,
            WalletValueObject(user.wallet.paymentType, user.wallet.money)
        )
    }
}
