package com.my.sparta.concert.aggregate.user.application.port.outbound

import com.my.sparta.concert.aggregate.user.application.domain.model.Users

interface SaveMoneyPort {

    fun saveMoney(user: Users): Users
}
