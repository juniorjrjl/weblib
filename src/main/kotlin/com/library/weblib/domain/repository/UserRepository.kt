package com.library.weblib.domain.repository

import com.library.weblib.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional


/**
 *
 * <code>{@link User}</code> repository
 *
*/
@Repository
interface UserRepository : JpaRepository<User, Long>{

    /**
     *
     * Find <code>{@link User}</code> by email
     * @param cpf must not be {@literal null}.
     * @return the <code>{@link User}</code> with the given nickname or {@literal Optional#empty()} if none found.
     *
     */
    fun findByCpf(cpf: String): Optional<User>

}