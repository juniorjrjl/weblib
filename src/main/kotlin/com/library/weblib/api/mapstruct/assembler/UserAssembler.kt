package com.library.weblib.api.mapstruct.assembler

import com.library.weblib.api.model.output.SingleUserModel
import com.library.weblib.domain.exception.UserNotFoundException
import com.library.weblib.domain.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 *
 * Interface to transform <code>{@link User}</code> in a DTO.
 *
 */
@Mapper(componentModel = "spring")
interface UserAssembler {


    /**
     *
     * Transform <code>{@link User}</code> in a <code>{@link SingleUserModel}</code>.
     * @param user must not be {@literal null}.
     * @return <code>{@link SingleUserModel}</code> with user data.
     *
     */
    @Mappings(
            Mapping(target = "id", source = "id"),
            Mapping(target = "name", source = "name"),
            Mapping(target = "email", source = "email"),
            Mapping(target = "cpf", source = "cpf"),
            Mapping(target = "createdAt", source = "createdAt"),
            Mapping(target = "updatedAt", source = "updatedAt")
    )
    fun toModel(user: User): SingleUserModel

}