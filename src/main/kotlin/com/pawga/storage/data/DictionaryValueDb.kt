package com.pawga.storage.data

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.data.annotation.*
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.Size

/**
 * Created by pawga777
 */
@Serdeable
@MappedEntity(value = "dictionary_value")
data class DictionaryValueDb(
    @GeneratedValue
    @field:Id val id: Long? = null,
    @Size(max = 80) val code: String,
    @Size(max = 255) val value: String,
    @MappedProperty(value = "dictionary_id")
    @JsonProperty(value = "dictionary_id")
    val dictionaryId: Long,
)
