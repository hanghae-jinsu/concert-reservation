package com.my.sparta.concert.aggregate.user.application.domain.model

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import lombok.Getter

@Getter
class Users(

    var userId: String,
    var username: String,
    var age: Int,
    var wallet: Wallet

)
