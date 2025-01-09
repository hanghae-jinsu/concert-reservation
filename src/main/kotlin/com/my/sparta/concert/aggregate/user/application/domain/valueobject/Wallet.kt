package com.my.sparta.concert.aggregate.user.application.domain.valueobject

import lombok.Getter

@Getter
 class Wallet(
    var paymentType: PaymentType,
    var money: Double,
) {

    fun chargeMoney(amount: Int): Unit {

        require(amount > 0) { "충전 금액은 0보다 커야 합니다." }
        this.money += amount

    }
}
