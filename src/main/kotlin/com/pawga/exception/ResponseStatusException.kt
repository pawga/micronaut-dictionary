package com.pawga.exception

import io.micronaut.http.HttpStatus

/**
 * Created by pawga777
 */
class ResponseStatusException(
    val status: HttpStatus,
    val reason: String,
) : RuntimeException()
