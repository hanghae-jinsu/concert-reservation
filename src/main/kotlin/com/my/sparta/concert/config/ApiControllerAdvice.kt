package com.my.sparta.concert.config

import org.apache.tomcat.websocket.AuthenticationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)


    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        return ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
            e.message.toString()
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleException(e: AuthenticationException): ErrorResponse {
        return ErrorResponse(
            HttpStatus.UNAUTHORIZED.value().toString(),
            e.message.toString()
        )
    }


}

data class ErrorResponse(val code: String, val message: String)

