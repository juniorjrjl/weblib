package com.library.weblib.api.controller

import com.library.weblib.api.exceptionhandler.Problem
import com.library.weblib.api.exceptionhandler.ProblemType
import com.library.weblib.domain.exception.UserNotFoundException
import com.library.weblib.domain.model.User
import com.library.weblib.domain.service.UserService
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.lenient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime


/**
 *
 *Class to test '/users' endpoints
 *
 */
@SpringBootTest
class UserControllerTest : AbstractControllerTest() {

    var userService: UserService = mock()

    @Autowired
    lateinit var userController: UserController

    @BeforeEach
    override fun setup(){
        super.setup()
        userController.userService = userService
    }

    /**
     *
     * check when found user by cpf then HttpStatus is OK and user is in body request
     *
     */
    @Test
    fun whenFoundUserByEmailThenReturnIt(){
        val user = User(1, "João", "joao@joao.com", "1234567890")
        lenient().`when`(userService.findByCpf(user.cpf)).thenReturn(user)
        val result : MvcResult = mockMvc.perform(get("/users/${user.cpf}")
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk).andReturn()
        val foundUser = result.response.contentAsString.deserialize<User>()
        Assertions.assertThat(foundUser).isNotNull
        Assertions.assertThat(foundUser.id).isEqualTo(user.id)
        Assertions.assertThat(foundUser.createdAt).isEqualTo(user.createdAt)
        Assertions.assertThat(foundUser.updatedAt).isEqualTo(user.updatedAt)
        Assertions.assertThat(foundUser.name).isEqualTo(user.name)
        Assertions.assertThat(foundUser.email).isEqualTo(user.email)
        Assertions.assertThat(foundUser.cpf).isEqualTo(user.cpf)
    }

    /**
     *
     * check when try find by not registered then return HttpStatus NotFound and message with details
     *
     */
    @Test
    fun whenNotFoundUserByEmailThenReturn404(){
        val cpf = "1111111111"
        val problemType = ProblemType.RESOURCE_NOT_FOUND
        val exceptionMessage = "The user with cpf $cpf wasn't not found"
        val problem = Problem.Builder(HttpStatus.NOT_FOUND.value(), OffsetDateTime.now())
                .problem(problemType).detail(exceptionMessage)
        lenient().`when`(userService.findByCpf(cpf)).thenThrow(UserNotFoundException(exceptionMessage))
        val result : MvcResult = mockMvc.perform(get("/users/$cpf")
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNotFound).andReturn()
        val returnedProblem = result.response.contentAsString.deserialize<Problem>()
        Assertions.assertThat(returnedProblem.status).isEqualTo(problem.status)
        Assertions.assertThat(returnedProblem.timestamp).isAfter(problem.timestamp)
        Assertions.assertThat(returnedProblem.title).isEqualTo(problem.title)
        Assertions.assertThat(returnedProblem.type).isEqualTo(problem.type)
        Assertions.assertThat(returnedProblem.detail).isEqualTo(problem.detail)
    }

}