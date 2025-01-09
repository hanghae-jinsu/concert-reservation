package com.my.sparta.concert

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class ConcertApplication

fun main(args: Array<String>) {
    runApplication<ConcertApplication>(*args)
}
