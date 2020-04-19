package com.library.weblib.api.controller

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * 
 * function injected in String type to get a json and cast to T
 * @param T a type class to return
 * 
 */
inline fun <reified T:Any> String.deserialize():T = jacksonObjectMapper().registerModule(JavaTimeModule())
        .readValue(this)