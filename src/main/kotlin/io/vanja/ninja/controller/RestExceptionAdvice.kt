package io.vanja.ninja.controller

import io.vanja.ninja.exception.AlreadyExistsException
import io.vanja.ninja.exception.DoesNotExistException
import io.vanja.ninja.util.RestError
import io.vanja.ninja.util.RestErrorUtil
import jakarta.validation.ConstraintViolationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class RestExceptionAdvice(private val restErrorUtil: RestErrorUtil) {
    @ExceptionHandler(value = [ConstraintViolationException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(e: ConstraintViolationException): RestError {
        return restErrorUtil.restError(e)
    }

    @ExceptionHandler(value = [AlreadyExistsException::class])
    @ResponseStatus(value = HttpStatus.CONFLICT)
    fun handleAlreadyExistsException(e: AlreadyExistsException): RestError {
        return restErrorUtil.restError(e)
    }

    @ExceptionHandler(value = [DoesNotExistException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleDoesNotExistsException(e: DoesNotExistException): RestError {
        return restErrorUtil.restError(e)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): RestError {
        return restErrorUtil.restError(e)
    }
}