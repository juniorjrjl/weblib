package com.library.weblib.domain.service

import com.library.weblib.domain.exception.UserNotFoundException
import com.library.weblib.domain.model.User
import com.library.weblib.domain.repository.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.ZonedDateTime

/**
*
*Class to check User operations
*
*/
@ExtendWith(SpringExtension::class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var user: User

    lateinit var userService: UserService

    /**
     *
     *Prepare a <code>{@link UserService}</code> instance and create a single data.
     *
     */
    @BeforeEach
    fun setup(){
        userService = UserService(userRepository)
        user = User(0, "João", "joao@gmail.com")
        userService.save(user)
    }

    /**
     *
     *Clear datas after each tests
     *
     */
    @AfterEach
    fun tearDown(){
        userRepository.deleteAll()
    }

    /**
    *
    * Check if user is saved
    */
    @Test
    fun saveTest(){
        val name = "Maria"
        val email = "maria@gmail.com"
        val user = User(0, name, email)
        val savedUser = userRepository.save(user)
        val currentTime = ZonedDateTime.now()
        Assertions.assertThat(savedUser).isNotNull
        Assertions.assertThat(savedUser.id).isGreaterThan(0)
        Assertions.assertThat(savedUser.createdAt).isBefore(currentTime)
        Assertions.assertThat(savedUser.updatedAt).isBefore(currentTime)
        Assertions.assertThat(savedUser.name).isEqualTo(name)
        Assertions.assertThat(savedUser.email).isEqualTo(email)
    }

    /**
    *
    * Check if user is updated
    */
    @Test
    fun updateTest(){
        val savedUser = userService.save(user)
        val storedUser = userService.findById(user.id)
        storedUser.name = "Juca"
        storedUser.email = "juca@gmail.com"
        val updatedUser = userService.save(storedUser)
        Assertions.assertThat(updatedUser).isNotNull
        Assertions.assertThat(updatedUser.id).isEqualTo(savedUser.id)
        Assertions.assertThat(updatedUser.name).isNotEqualTo(savedUser.name)
        Assertions.assertThat(updatedUser.email).isNotEqualTo(savedUser.email)
        Assertions.assertThat(updatedUser.createdAt).isEqualTo(savedUser.createdAt)
        Assertions.assertThat(updatedUser.updatedAt).isAfter(savedUser.updatedAt)
    }

    /**
    *
    * Check if user is deleted
    */
    @Test
    fun deleteTest(){
        assertDoesNotThrow { userService.delete(user.id) }
        assertThrows<UserNotFoundException> { userService.findById(user.id) }
    }

    /**
    *
    * Check find all users
    */
    @Test
    fun selectAll(){
        val users = userService.find()
        Assertions.assertThat(users).isNotEmpty
    }

    /**
    *
    * Check if find with id not used throws <code>{@link UserNotFoundException}</code>
    */
    @Test
    fun findUnknowUser(){
        assertThrows<UserNotFoundException> { userService.findById(99) }
    }

}