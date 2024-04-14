package com.pawga.domain.model

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.Size

/**
 * Created by pawga777
 */
@Serdeable
data class Dictionary(
    val id: Long?,
    @Size(max = 255) val name: String,
    val values: List<DictionaryValue>,
) {
    constructor(id: Long? = null, name: String) : this(id, name, emptyList())
}
