package com.my.sparta.concert.aggregate.user.application.port.outbound

import com.my.sparta.concert.aggregate.user.application.domain.model.Users

interface SaveUserInfoPort {

    fun saveUserInfo(user: Users): Users
}
