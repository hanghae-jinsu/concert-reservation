package com.my.sparta.concert.common.util

import java.lang.annotation.Documented

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Documented
annotation class LockExecution(val paramName: String, val fieldName: String)
