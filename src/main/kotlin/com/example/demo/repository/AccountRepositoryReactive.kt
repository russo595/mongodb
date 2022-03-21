package com.example.demo.repository

import com.example.demo.model.Account
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface AccountRepositoryReactive : ReactiveMongoRepository<Account, String> {

    fun findAllByValue(value: String): Flux<Account>

    fun findFirstByOwner(owner: Mono<String>): Mono<Account>
}