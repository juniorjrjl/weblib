package com.library.weblib.api.controller

import com.library.weblib.api.mapstruct.assembler.UserAssembler
import com.library.weblib.api.mapstruct.disassembler.UserDisassembler
import com.library.weblib.api.model.input.UserInput
import com.library.weblib.api.model.output.SingleUserModel
import com.library.weblib.domain.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 *
 * Class to response requests send to endpoint '/users/'
 *
 */
@RestController
@RequestMapping(path = ["/users"])
class UserController(
        @Autowired var userService: UserService,
        @Autowired var userAssembler: UserAssembler,
        @Autowired var userDisassembler: UserDisassembler
) {

    /**
     *
     * GET response to '/user/{nickname}'
     * @param nickname e-mail to find a user
     * @return A user with userEmail
     *
     */
    @GetMapping("{cpf}")
    fun findByCpf(@PathVariable cpf: String): SingleUserModel = userAssembler.toModel(userService.findByCpf(cpf))


    /**
     *
     * POST response to '/user/'
     * @param dto users data to save
     * @return A saved user
     *
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun create(@RequestBody @Valid dto: UserInput): SingleUserModel{
        val user = userDisassembler.toModel(dto)
        val savedUser = userService.save(user)
        return userAssembler.toModel(savedUser)
    }

}