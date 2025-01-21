package com.my.sparta.concert.aggregate.user.application.port.outbound

interface LoadNonExpiredTokenPort {

    fun validateActiveTokens(tokenString: Set<String>): Set<String>

}
