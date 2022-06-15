package com.example.demo.config

import com.example.demo.model.Account
import com.example.demo.repository.AccountRepository
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate
import com.mongodb.client.model.InsertOneModel
import com.mongodb.client.model.WriteModel
import org.bson.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.concurrent.ThreadLocalRandom

@ChangeLog(order = "001")
class DatabaseChangeLog {

    val mapper = ObjectMapper().apply {
        registerModule(KotlinModule.Builder().build())
        setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
    }

    @ChangeSet(order = "001", id = "#{Instant.now().toEpochMilli()}", author = "Rustem Sabitov", runAlways = false)
    fun init(accountRepository: AccountRepository) {
        accountRepository.findAll().forEach {
            it.value = it.value.plus(BigDecimal(1000))
            accountRepository.save(it)
        }
    }

    @ChangeSet(order = "002", id = "002", author = "Rustem Sabitov", runAlways = false)
    fun init2(mongoTemplate: MongockTemplate) {
        mongoTemplate.db.createCollection("test1")
        mongoTemplate.db.createCollection("test2")
    }

    @ChangeSet(order = "003", id = "003", author = "Rustem Sabitov", runAlways = false)
    fun init3(mongoTemplate: MongockTemplate) {
        val mutableListOf1: MutableList<WriteModel<out Document>> = mutableListOf()
        mutableListOf1.add(InsertOneModel(Document.parse(mapper.writeValueAsString(Account(owner = "qwerty")))))
        mongoTemplate.getCollection("account").bulkWrite(mutableListOf1)
    }

    @ChangeSet(order = "004", id = "004", author = "Rustem Sabitov", runAlways = false)
    fun init4(mongoTemplate: MongockTemplate) {
        val mutableListOf1: MutableList<WriteModel<out Document>> = mutableListOf()
        mutableListOf1.add(InsertOneModel(Document.parse(mapper.writeValueAsString(Account(owner = "qwerty")))))
        mongoTemplate.getCollection("account").bulkWrite(mutableListOf1)
    }

    @ChangeSet(order = "005", id = "005", author = "Rustem Sabitov", runAlways = false)
    fun init5(mongoTemplate: MongockTemplate) {
        val mutableListOf1: MutableList<WriteModel<out Document>> = mutableListOf()
        mutableListOf1.add(InsertOneModel(Document.parse(mapper.writeValueAsString(Account(owner = "qwerty22")))))
        mongoTemplate.getCollection("account").bulkWrite(mutableListOf1)
    }

    @ChangeSet(order = "008", id = "013", author = "Rustem Sabitov", runAlways = false)
    fun init8(mongoTemplate: MongockTemplate) {
        val find: MutableList<Account> =
            mongoTemplate.find(Query.query(Criteria.where("value").lte(1000)), Account::class.java)
        find.forEach { println(it) }
    }

    @ChangeSet(order = "009", id = "014", author = "Rustem Sabitov", runAlways = false)
    fun init9(mongoTemplate: MongockTemplate) {

        val d1 = LocalDate.of(2022, 5, 14).toEpochSecond(LocalTime.MIN, ZoneOffset.UTC)
        val d2 = LocalDate.of(2020, 5, 14).toEpochSecond(LocalTime.MAX, ZoneOffset.UTC)
        val i1: Instant = Instant.ofEpochSecond(d1)
        val i2: Instant = Instant.ofEpochSecond(d2)

        mongoTemplate.findAll(Account::class.java)
            .forEach {
                mongoTemplate.save(it.apply {
                    it.date =
                        Instant.ofEpochMilli(ThreadLocalRandom.current().nextLong(i2.toEpochMilli(), i1.toEpochMilli()))
                })
            }
    }
}