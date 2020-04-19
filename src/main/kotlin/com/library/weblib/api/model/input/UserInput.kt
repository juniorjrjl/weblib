package com.library.weblib.api.model.input

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.math.min

/**
 *
 *  POKO to package a user's data
 *
 */
class UserInput {
    @field:NotBlank
    @field:Size(min = 1, max = 150)
    var name: String = ""

    @field:NotBlank
    @field:Email
    @field:Size(max = 200)
    var email: String = ""

    @field:NotBlank
    @field:Size(min = 11, max = 11)
    var cpf: String = ""
}