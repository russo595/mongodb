package com.example.demo.repository

import com.example.demo.model.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : MongoRepository<Account, String> {

    fun findAllByValue(value: String): List<Account>

    fun findFirstByOwner(owner: String): Account
}