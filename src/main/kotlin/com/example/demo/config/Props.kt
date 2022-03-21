package com.example.demo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "test")
data class Props(
    val map: Map<TestEnum, Int>
)