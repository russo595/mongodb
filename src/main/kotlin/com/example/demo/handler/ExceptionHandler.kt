package com.example.demo.handler

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): ResponseEntity<ErrorDetail> {
        return ResponseEntity(ErrorDetail(e.message), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        return super.handleExceptionInternal(ex, ErrorDetail(ex.message), headers, status, request)
    }
}

class ErrorDetail(val message: String?)