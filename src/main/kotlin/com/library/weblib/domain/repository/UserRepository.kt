package com.library.weblib.domain.repository

import com.library.weblib.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
* <code>{@link User}</code> repository
*/
@Repository
interface UserRepository : JpaRepository<User, Long>