package com.library.weblib.api.exceptionhandler

import com.library.weblib.domain.exception.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime
import java.util.stream.Collectors


/**
 *
 * Class to deal with api exceptions and return information about it
 *
 */
@ControllerAdvice
class ApiExceptionHandler(@Autowired var messageSource: MessageSource): ResponseEntityExceptionHandler() {

    /**
     *
     * handle Exception when a entity is not found
     * @param ex A threw exception
     * @param request The request make by client
     * @return response from request who threw exception
     *
     */
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException, request: WebRequest?): ResponseEntity<*>? {
        val status: HttpStatus = HttpStatus.NOT_FOUND
        val problem: Problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, ex.message!!)
                .userMessage(ex.message!!)
                .build()
        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request!!)
    }

    /**
     *
     * handle Exception when a client send some invalid argument
     * @param ex A threw exception
     * @param request The request make by client
     * @return response from request who threw exception
     *
     */
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders,
                                                        status: HttpStatus, request: WebRequest): ResponseEntity<Any> =
        buildResponseBodyWithValidateErrors(ex.bindingResult, status, request, ex);

    /**
     *
     * Build a response from request with invalid arguments
     * @param bindingResult object with constraints names and validations
     * @param status The HttpStatus to response to client
     * @param request The request make by client
     * @param ex Exception threw by contraint violation
     * @return response from request who threw exceptionresponse from request who threw exception
     *
     */
    private fun buildResponseBodyWithValidateErrors(bindingResult: BindingResult, status: HttpStatus, request:
        WebRequest, ex: Exception): ResponseEntity<Any> {
        val detail = messageSource.getMessage("apiExceptionHandler.fieldErrors", emptyArray(), LocaleContextHolder.getLocale())
        val problemFields: MutableList<Object> = bindingResult.allErrors.stream()
                .map {
                    objectError: ObjectError ->
                    val message: String = messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
                    var name = objectError.objectName
                    if (objectError is FieldError) {
                        name = objectError.field
                    }
                    Object(name, message)
                }
                .collect(Collectors.toList())
        val problem = createProblemBuilder(status, ProblemType.INVALID_DATA, detail).userMessage(detail)
                .fields(problemFields).build()
        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    /**
     *
     * build a object who will be add to body
     * @param status The HttpStatus to response to client
     * @param problemType enum with information about the problem
     * @param detail a message with problem explanation
     * @return <code>{@link Problem.Builder}</code> with pre defined information
     *
     */
    private fun createProblemBuilder(status: HttpStatus, problemType: ProblemType, detail: String): Problem.Builder =
            Problem.Builder(status.value(), OffsetDateTime.now()).problem(problemType).detail(detail)

}