package com.my.sparta.concert.common.config

import com.my.sparta.concert.common.interceptor.AuthenticationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authenticationInterceptor: AuthenticationInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
            .addPathPatterns("/**") // 모든 경로에 대해 적용
            .excludePathPatterns("/public/**") // 제외 경로 설정
    }

}
