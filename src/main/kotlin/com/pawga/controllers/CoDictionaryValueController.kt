package com.pawga.controllers

import com.pawga.domain.model.ShortDictionaryValue
import com.pawga.domain.model.DictionaryValue
import com.pawga.domain.service.CoStorageService
import com.pawga.storage.extensions.toResponse
import com.pawga.storage.service.CoDictionaryValueService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpHeaders
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import kotlinx.coroutines.flow.Flow
import java.net.URI

/**
 * Created by pawga777
 */
@Controller("/api/v1/co-dictionary-value")
class CoDictionaryValueController(private val dictionaryService: CoStorageService<DictionaryValue, Long>) {

    @Get("/list-pageable")
    suspend fun list(@Valid pageable: Pageable): Page<DictionaryValue> = dictionaryService.findAll(pageable)

    @Get("/list")
    fun findAll(): Flow<DictionaryValue> = dictionaryService.findAll()

    @Post
    suspend fun save(@NotBlank @Body value: ShortDictionaryValue): HttpResponse<DictionaryValue> {
        return createDictionaryValue(dictionaryService.save(value.toResponse()))
    }

    @Get("/{id}")
    suspend fun get(id: Long): DictionaryValue? = dictionaryService.get(id)

    @Put
    suspend fun update(@Valid @Body obj: DictionaryValue): HttpResponse<*> {
        return dictionaryService.update(obj).run {
            HttpResponse
                .noContent<Any>()
                .header(HttpHeaders.LOCATION, location(obj.id).path)
        }
    }

    @Delete("/{id}")
    suspend fun delete(id: Long): Long = dictionaryService.delete(id)

    private fun createDictionaryValue(dictionary: DictionaryValue): MutableHttpResponse<DictionaryValue> {
        return HttpResponse
            .created(dictionary)
            .headers { headers: MutableHttpHeaders ->
                headers.location(location(dictionary))
            }
    }

    private fun location(id: Long?): URI {
        return URI.create("/api/v1/dictionary/$id")
    }

    private fun location(dictionary: DictionaryValue): URI {
        return location(dictionary.id)
    }
}