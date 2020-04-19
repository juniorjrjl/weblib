package com.library.weblib.domain.exception

/**
 *
 *The generic exception when entity wasn't found
 *
 */
open class EntityNotFoundException: BuisnessException {

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