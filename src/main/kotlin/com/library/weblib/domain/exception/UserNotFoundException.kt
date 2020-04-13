package com.library.weblib.domain.exception

/**
 *
 *The exception who throws when try find a <code>{@link User}</code> by unknown id
 *@param id used to find
 */
class UserNotFoundException(id: Long): BuisnessException("The user with id $id wasn't found")