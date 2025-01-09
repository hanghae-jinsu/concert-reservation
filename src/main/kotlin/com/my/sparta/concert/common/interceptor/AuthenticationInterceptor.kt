package com.my.sparta.concert.common.interceptor

import com.my.sparta.concert.common.util.TokenUtilService
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthenticationInterceptor(
    private val tokenUtilService: TokenUtilService
) : HandlerInterceptor {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun init() {
        tokenUtilService.loadInitialTokens();
    }

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val token = request.getHeader("Authorization") // 헤더에서 토큰 값 가져오기

        logger.info("@@@@@@@@ preHandle token validate @@@@@@@@@@")

        require(!token.isNullOrBlank()) {
            "token이 header에 담겨있지 않습니다."
        }

        // 토큰 검증 로직
        if (!tokenUtilService.validateToken(token)) {

            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Unauthorized: Invalid token")
            return false
        }

        return true
    }

}
