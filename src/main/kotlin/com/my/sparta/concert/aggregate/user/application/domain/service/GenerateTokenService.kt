package com.my.sparta.concert.aggregate.user.application.domain.service

import com.my.sparta.concert.aggregate.user.application.port.inbound.GenerateTokenUseCase
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveUserTokenPort
import com.my.sparta.concert.common.util.TokenUtilService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// facade 계층을 어디 둬야 할까
@Slf4j
@Service
@RequiredArgsConstructor
class GenerateTokenService(
    val loadUserInfoPort: LoadUserInfoPort,
    val saveUserTokenPort: SaveUserTokenPort,
    val tokenUtilService: TokenUtilService,
) : GenerateTokenUseCase {

    @Transactional
    override fun generateToken(userId: String): String {
        val userInfo = loadUserInfoPort.getUserInfoById(userId)

        val generateToken = tokenUtilService.generateToken(userInfo.userId)

        return saveUserTokenPort.saveUserToken(generateToken)
    }
}
