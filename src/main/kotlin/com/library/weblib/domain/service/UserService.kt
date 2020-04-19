package com.library.weblib.domain.service

import com.library.weblib.domain.exception.UserNotFoundException
import com.library.weblib.domain.model.User
import com.library.weblib.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *
 * Class used by controller for management <code>{@link User}</code>
 * @param userRepository must not be {@literal null}. Repository with database operations.
 */
@Service
class UserService(@Autowired val userRepository: UserRepository) {

    /**
     *
     * find <code>{@link User}</code> by {@literal email}.
     * @param email must not be {@literal null}.
     * @throws UserNotFoundException in case the haven't <code>{@link User}</code> with {@literal email}.
     * @return <code>{@link User}</code> with {@literal email}.
     *
     */
    fun findByCpf(cpf: String): User = userRepository.findByCpf(cpf)
            .orElseThrow{ UserNotFoundException("The user with cpf $cpf wasn't not found") }

    /**
     *
     * find <code>{@link User}</code> by {@literal id}.
     * @param id must not be {@literal null}.
     * @throws UserNotFoundException in case the haven't <code>{@link User}</code> with {@literal id}.
     * @return <code>{@link User}</code> with {@literal id}.
     *
     */
    fun findById(id: Long): User = userRepository.findById(id).orElseThrow{ UserNotFoundException(id) }

    /**
     *
     * find all stored <code>{@link User}</code>.
     * @return <code>{@link MutableList<User>}</code> with {@literal id}.
     *
     */
    fun find(): MutableList<User> = userRepository.findAll()

    /**
     *
     * Create a new <code>{@link User}</code> or update if <code>{@link User.id}</code> has value.
     * @param user must not be {@literal null}.
     * @return <code>{@link User}</code> who was persisted.
     *
     */
    @Transactional
    fun save(user: User): User = userRepository.save(user)

    /**
     *
     * delete <code>{@link User}</code> by {@literal id}.
     * @param id must not be {@literal null}.
     * @throws UserNotFoundException in case the haven't <code>{@link User}</code> with {@literal id}.
     *
     */
    @Transactional
    fun delete(id: Long){
        val user: User = findById(id)
        userRepository.delete(user)
        userRepository.flush()
    }

}