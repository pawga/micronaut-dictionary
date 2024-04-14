package com.pawga.domain.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.Size

/**
 * Created by pawgq777
 */
@Serdeable
data class DictionaryValue(
    val id: Long = 0L,
    @JsonProperty("parent_id")
    val parentId: Long,
    @Size(max = 80) val code: String,
    @Size(max = 255) val value: String,
)

@Serdeable
data class ShortDictionaryValue(
    @JsonProperty("parent_id")
    val parentId: Long,
    @Size(max = 80) val code: String,
    @Size(max = 255) val value: String,
)