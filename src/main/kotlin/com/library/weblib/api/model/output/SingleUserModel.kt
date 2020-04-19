package com.library.weblib.api.model.output

import java.time.ZonedDateTime

/**
 *
 *  POKO to return a single user data
 *
 */
class SingleUserModel {
    var id: Long = 0
    var name: String = ""
    var email: String = ""
    var cpf: String = ""
    var createdAt: ZonedDateTime = ZonedDateTime.now()
    var updatedAt: ZonedDateTime = ZonedDateTime.now()
}