package com.library.weblib.domain.exception

/**
 *
 *The most generic application exception
 *
 */
open class BuisnessException: RuntimeException{

    /**
     *
     *@param message with exception information.
     *
     */
    constructor(message: String) : super(message) {}

    /**
     *
     *@param message with exception information.
     *@param cause the parent exception.
     *
     */
    constructor(message: String, cause: Throwable) : super(message, cause) {}

}