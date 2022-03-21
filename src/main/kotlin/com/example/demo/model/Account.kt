package com.example.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

@Document(value = "account")
data class Account(
    @Id
    val id: String? = null,
    @Indexed
    val owner: String,
    var value: BigDecimal = BigDecimal(Random.nextDouble(1.0, 1000.0)).setScale(2, RoundingMode.HALF_UP)
)
