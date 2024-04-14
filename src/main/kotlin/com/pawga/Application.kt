package com.pawga

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
    info = Info(
            title = "micronaut-dictionary",
            version = "1.0"
    )
)
object Api {
}
fun main(args: Array<String>) {
	run(*args)
}

