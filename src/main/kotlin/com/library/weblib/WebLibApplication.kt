package com.library.weblib

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebLibApplication

fun main(args: Array<String>) {
	runApplication<WebLibApplication>(*args)
}
