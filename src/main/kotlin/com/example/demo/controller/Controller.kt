package com.example.demo.controller

import com.example.demo.config.Props
import com.example.demo.model.Account
import com.example.demo.repository.AccountRepository
import com.example.demo.repository.AccountRepositoryReactive
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

@RestController
class Controller(val repository: AccountRepositoryReactive, val simpleRepository: AccountRepository, val pros: Props) {

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    @GetMapping("/all")
    @Transactional(readOnly = true)
    fun getAll(): Flux<Account> {
        pros.map.forEach { println("Key ${it.key}, value ${it.value}") }
        return repository.findAll()
    }

    @GetMapping("/random")
    @Transactional
    fun random(): Flux<Account> {
        val fromIterable: Flux<Account> = Flux.fromIterable(Iterable {
            val mutableListOf = mutableListOf<Account>()

            for (i in 1..3) {
                val joinToString = (1..10)
                    .map { Random.nextInt(0, charPool.size) }
                    .asSequence()
                    .map { charPool[it] }
                    .joinToString("")
                val bigDecimal: BigDecimal = BigDecimal(Random.nextDouble(1.0, 100.0)).setScale(2, RoundingMode.HALF_UP)
                mutableListOf.add(Account(owner = joinToString, value = bigDecimal))
            }
            mutableListOf.iterator()
        })
        return repository.saveAll(fromIterable)
    }

    @GetMapping("/random2")
    @Transactional
    fun random2(): List<Account> {
        val mutableListOf = mutableListOf<Account>()
        for (i in 1..3) {
            val joinToString = (1..10)
                .map { Random.nextInt(0, charPool.size) }
                .asSequence()
                .map { charPool[it] }
                .joinToString("")
            val bigDecimal = BigDecimal(Random.nextDouble(1.0, 100.0)).setScale(2, RoundingMode.HALF_UP)
            mutableListOf.add(Account(owner = joinToString, value = bigDecimal))
        }

        return simpleRepository.saveAll(mutableListOf)
    }
}