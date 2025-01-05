package com.my.sparta.concert.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@OpenAPIDefinition(
    info =
    io.swagger.v3.oas.annotations.info.Info(
        title = "concert reservation server",
        description = "Concert Reservation API Documentation",
        version = "v1.0.0",
    ),
    servers =
    arrayOf(
        Server(
            url = "localhost:8080",
            description = "local API",
        ),
    ),
    tags =
    arrayOf(
        Tag(name = "concert API", description = "concert API"),
    ),
)
@Configuration
class OpenApiConfig
