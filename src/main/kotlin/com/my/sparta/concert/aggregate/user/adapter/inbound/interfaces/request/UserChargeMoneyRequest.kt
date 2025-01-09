package com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.request

import lombok.Data

@Data
class UserChargeMoneyRequest(

     val userId: String,
     val money: Double,
     val paymentType: String

)
