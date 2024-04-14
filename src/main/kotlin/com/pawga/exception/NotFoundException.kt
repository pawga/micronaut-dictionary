package com.pawga.exception

import java.lang.RuntimeException

class NotFoundException : RuntimeException {
    constructor() : super()

    constructor(message: String) : super(message)
}
