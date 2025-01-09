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
            .addPathPatterns("/user/**","/concert/**","/reservation/**") // 모든 경로에 대해 적용
            .excludePathPatterns(
                "/public/**", "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html#/**"
            ) 
    }

}
