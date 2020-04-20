package com.library.weblib.api.controller

import com.library.weblib.api.exceptionhandler.Problem
import com.library.weblib.api.exceptionhandler.ProblemType
import com.library.weblib.api.model.input.UserInput
import com.library.weblib.api.model.output.SingleUserModel
import com.library.weblib.domain.exception.UserNotFoundException
import com.library.weblib.domain.model.User
import com.library.weblib.domain.service.UserService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.lenient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime
import java.time.ZonedDateTime


/**
 *
 *Class to test '/users' endpoints
 *
 */
@SpringBootTest
class UserControllerTest : AbstractControllerTest() {

    @Autowired
    lateinit var userController: UserController

    @Autowired
    lateinit var messageSource: MessageSource

    @BeforeEach
    override fun setup(){
        super.setup()
    }

    /**
     *
     * check when found user by cpf then HttpStatus is OK and user is in body request
     *
     */
    @Test
    fun whenFoundUserByEmailThenReturnIt(){
        val user = User(1, "João", "joao@joao.com", "1234567890")
        val userService: UserService = mock()
        userController.userService = userService
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
        val userService: UserService = mock()
        userController.userService = userService
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

    companion object{
        @JvmStatic
        fun invalidUsers() =
        listOf(
                Arguments.of(
                        UserInput("",
                                "${String.random(1)}@teste.com",
                                String.random(11)),
                        "NotBlank.userInput.name",
                        listOf("user name")
                ),
                Arguments.of(
                        UserInput(String.random(151),
                                "${String.random(1)}@teste.com",
                                String.random(11)),
                        "Size.userInput.name",
                        listOf("user name", "150", "1")
                ),
                Arguments.of(
                        UserInput(" ",
                                "${String.random(1)}@teste.com",
                                String.random(11)),
                        "NotBlank.userInput.name",
                        listOf("user name")
                ),
                Arguments.of(
                        UserInput(String.random(150), String.random(200), String.random(11)),
                        "Email.userInput.email",
                        listOf("user e-mail")
                ),
                Arguments.of(
                        UserInput(String.random(150), "", String.random(11)),
                        "NotBlank.userInput.email",
                        listOf("user e-mail")
                ),
                Arguments.of(
                        UserInput(String.random(150), "  ", String.random(11)),
                        "NotBlank.userInput.email",
                        listOf("user e-mail")
                ),
                Arguments.of(
                        UserInput(String.random(1),
                                "${String.random(191)}@teste.com",
                                String.random(11)),
                        "Size.userInput.email",
                        listOf("user e-mail", "200")
                ),
                Arguments.of(
                        UserInput(String.random(1),
                                "${String.random(1)}@teste.com",
                                "           "),
                        "NotBlank.userInput.cpf",
                        listOf("user CPF", "11")
                ),
                Arguments.of(
                        UserInput(String.random(1),
                                "${String.random(1)}@teste.com",
                                String.random(10)),
                        "Size.userInput.cpf",
                        listOf("user CPF", "11")
                ),
                Arguments.of(
                        UserInput(String.random(1),
                                "${String.random(1)}@teste.com",
                                String.random(12)),
                        "Size.userInput.cpf",
                        listOf("user CPF", "11")
                )
        )
    }

    /**
     *
     * Check the <code>{@link UserInput}</code> constraints
     * @param user the object with contraints errors
     * @param errorMessage expected error message
     * @param args arguments used by errorMessage
     *
     */
    @ParameterizedTest
    @MethodSource("invalidUsers")
    fun checkInsertConstraints(user: UserInput, errorMessage: String, args: List<String>){
        val result : MvcResult = mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.serialize())
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isBadRequest).andReturn()
        val message = messageSource.getMessage(errorMessage, args.toTypedArray(), LocaleContextHolder.getLocale())
        print(result.response)
        Assertions.assertThat(result.response.contentAsString).asString().contains(message)
    }

    /**
     *
     *Check when the user was stored the endpoint return a object with ID
     *
     */
    @Test
    fun checkInsertReturn(){
        val userService: UserService = mock()
        userController.userService = userService
        val name = "João"
        val email = "joao@joao.com"
        val cpf = "12345678900"
        val currentDateTime = ZonedDateTime.now()
        val user = UserInput(name, email, cpf)
        val userMockReturn = User(1L, name, email, cpf)
        userMockReturn.createdAt = currentDateTime
        userMockReturn.updatedAt = currentDateTime
        lenient().`when`(userService.save(any())).thenReturn(userMockReturn)
        val result : MvcResult = mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.serialize())
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated).andReturn()
        val returnedUser = result.response.contentAsString.deserialize<SingleUserModel>()
        Assertions.assertThat(returnedUser).isNotNull
        Assertions.assertThat(returnedUser.id).isEqualTo(1L)
        Assertions.assertThat(returnedUser.name).isEqualTo(name)
        Assertions.assertThat(returnedUser.email).isEqualTo(email)
        Assertions.assertThat(returnedUser.cpf).isEqualTo(cpf)
        Assertions.assertThat(returnedUser.createdAt).isEqualTo(currentDateTime)
        Assertions.assertThat(returnedUser.updatedAt).isEqualTo(currentDateTime)
    }

}
