package com.library.weblib.api.exceptionhandler

import com.library.weblib.domain.exception.EntityNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime

/**
 *
 * Class to deal with api exceptions and return information about it
 *
 */
@ControllerAdvice
class ApiExceptionHandler: ResponseEntityExceptionHandler() {

    /**
     *
     * handle Exception when a entity is not found
     * @param ex A threw exception
     * @param request A web request
     * @return response from request who threw exception
     *
     */
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotfoundException(ex: EntityNotFoundException, request: WebRequest?): ResponseEntity<*>? {
        val status: HttpStatus = HttpStatus.NOT_FOUND
        val problem: Problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, ex.message!!)
                .userMessage(ex.message!!)
                .build()
        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request!!)
    }

    /**
     *
     * build a object who will be add to body
     * @param status the request HttpStatus
     * @param problemType enum with information about the problem
     * @param detail a message with problem explanation
     * @return <code>{@link Problem.Builder}</code> with pre defined information
     *
     */
    private fun createProblemBuilder(status: HttpStatus, problemType: ProblemType, detail: String): Problem.Builder =
            Problem.Builder(status.value(), OffsetDateTime.now()).problem(problemType).detail(detail)

}