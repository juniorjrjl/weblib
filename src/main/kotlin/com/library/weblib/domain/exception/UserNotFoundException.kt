package com.library.weblib.domain.exception

/**
 *
 *The exception who throws when try find a <code>{@link User}</code> by unknown id
 */
class UserNotFoundException: EntityNotFoundException{

    /**
     *
     *@param id used to try found.
     *
     */
    constructor(id: Long) : super("The user with id $id wasn't found")

    /**
     *
     *@param message with exception information.
     *
     */
    constructor(message: String) : super(message)
}