package com.my.sparta.concert.aggregate.user.application.port.inbound

import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserUseMoneyCommand

interface UserUseMoneyUseCase {

    fun useMoney(command: UserUseMoneyCommand): Users

    fun useMoneyNoneReentrantLock(command: UserUseMoneyCommand): Users
}
