package com.pawga.controllers

import com.pawga.domain.model.Dictionary
import com.pawga.domain.model.DictionaryValue
import com.pawga.domain.service.CoStorageChildrenService
import com.pawga.domain.service.CoStorageService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.notFound
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.MutableHttpHeaders
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.net.URI

/**
 * Created by pawga777
 */
@Controller("/api/v1/co-dictionary")
open class CoDictionaryController(
    private val service: CoStorageService<Dictionary, Long>,
    private val dictionaryValueService: CoStorageChildrenService<DictionaryValue, Long>,
) {

    @Get("/list")
    fun findAll(): Flow<Dictionary> = service.findAll()

    @Get("/list-pageable")
    open suspend fun list(@Valid pageable: Pageable): Page<Dictionary> = service.findAll(pageable)

    @Get("/list-with-values")
    fun getAll(): Flow<Dictionary> {
        return service.findAll().mapNotNull(::readDictionary)
    }

    // todo для статьи
    @Get("/stream")
    fun stream(): Flow<Int> =
        flowOf(1,2,3)
            .onEach { delay(700) }

    @Post
    suspend fun save(@NotBlank name: String): HttpResponse<Dictionary> {
        return createDictionary(service.save(Dictionary(name = name)))
    }

    @Get("/{id}")
    suspend fun get(id: Long): HttpResponse<Any> {
        val dictionary = service.get(id) ?: return notFound()
        return ok(dictionary)
    }

    @Put
    suspend fun update(@Valid @Body obj: Dictionary): HttpResponse<*> {
        return service.update(obj).run {
            HttpResponse
                .noContent<Any>()
                .header(HttpHeaders.LOCATION, location(obj.id).path)
        }
    }

    @Delete("/{id}")
    suspend fun delete(id: Long): Long = service.delete(id)

    // private

    private suspend fun readDictionary(dictionary: Dictionary): Dictionary {
        if (dictionary.id == null) return dictionary
        val values = dictionaryValueService.findAllByDictionaryId(dictionary.id).toList()
        if (values.isEmpty()) return dictionary
        return dictionary.copy(
            values = values
        )
    }

    private fun createDictionary(dictionary: Dictionary): MutableHttpResponse<Dictionary> {
        return HttpResponse
            .created(dictionary)
            .headers { headers: MutableHttpHeaders ->
                headers.location(location(dictionary))
            }
    }

    private fun location(id: Long?): URI {
        return URI.create("/api/v1/co-dictionary/$id")
    }

    private fun location(dictionary: Dictionary): URI {
        return location(dictionary.id)
    }
}