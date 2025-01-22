package com.my.sparta.concert.aggregate.user.adapter.inbound.web.interfaces.response

import lombok.Data

@Data
class GetTokenResponse(
    var token: String,
)
