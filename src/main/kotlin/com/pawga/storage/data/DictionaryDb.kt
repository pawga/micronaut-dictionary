package com.pawga.storage.data

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.Size

/**
 * Created by pawga777
 */

@Serdeable
@MappedEntity(value = "dictionary")
data class DictionaryDb(
    @GeneratedValue
    @field:Id val id: Long? = null,
    @Size(max = 255) val name: String,
)
