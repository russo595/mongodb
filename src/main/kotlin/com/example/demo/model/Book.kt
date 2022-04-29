package com.example.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "book")
data class Book(
    @Id
    val id: String? = null,
    val name: String,
)
