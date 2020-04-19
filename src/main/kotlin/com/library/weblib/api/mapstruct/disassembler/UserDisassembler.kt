package com.library.weblib.api.mapstruct.disassembler

import com.library.weblib.api.model.input.UserInput
import com.library.weblib.domain.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 *
 * Interface to transform DTO in a <code>{@link User}</code>.
 *
 */
@Mapper(componentModel = "spring")
interface UserDisassembler {


    /**
     *
     * Transform <code>{@link UserInput}</code> in a <code>{@link User}</code>.
     * @param userInput must not be {@literal null}.
     * @return <code>{@link User}</code> with userInput data.
     *
     */
    @Mappings(
            Mapping(target = "id", constant = "0L"),
            Mapping(target = "name", source = "name"),
            Mapping(target = "email", source = "email") ,
            Mapping(target = "cpf", source = "cpf")
    )
    fun toModel(userInput: UserInput):User

}