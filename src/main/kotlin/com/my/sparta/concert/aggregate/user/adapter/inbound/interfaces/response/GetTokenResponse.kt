package com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response

import lombok.Data

@Data
class GetTokenResponse(
    var token: String,
)
