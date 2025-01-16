package com.my.sparta.concert

import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

@Configuration
class TestcontainersConfiguration {

    @PreDestroy
    fun preDestroy() {
        if (mySqlContainer.isRunning) mySqlContainer.stop()
    }

    companion object {

        val mySqlContainer: MySQLContainer<*> =
            MySQLContainer(DockerImageName.parse("mysql:latest"))
                .withDatabaseName("concert")
                .withUsername("users")
                .withPassword("password")
                .withInitScript("static/data/concert_script.sql")
                .apply {
                    start()
                }

        init {
            System.setProperty(
                "spring.datasource.url",
                mySqlContainer.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=Asia/Seoul",
            )
            System.setProperty("spring.datasource.username", mySqlContainer.username)
            System.setProperty("spring.datasource.password", mySqlContainer.password)
        }
    }
}
