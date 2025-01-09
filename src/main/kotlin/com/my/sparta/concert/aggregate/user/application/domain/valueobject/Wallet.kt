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

    fun useWallet(cost: Double, count: Int) {
        val totalCost = count * cost

        require(count > 0) {
            "사람 인원 수는 0보다 작을수 없습니다. "
        }

        require(totalCost < this.money) {
            "지갑에 돈이 없어서 $cost 해당 영화비를 낼 수 없습니다."
        }
        this.money -= totalCost
    }
}
