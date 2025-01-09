package com.my.sparta.concert.aggregate.user.application.port.inbound

import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.port.inbound.command.UserChargeCommand

interface UserChargeMoneyUseCase {

    fun chargeMoney(command : UserChargeCommand): Users
}
