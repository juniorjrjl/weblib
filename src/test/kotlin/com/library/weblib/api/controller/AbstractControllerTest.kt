package com.library.weblib.api.controller

import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 *
 * abstract class to use with base to create controller test classes
 *
 */
@SpringBootTest
abstract class AbstractControllerTest {

    @Autowired
    protected lateinit var webApplicationContext: WebApplicationContext

    protected lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

}