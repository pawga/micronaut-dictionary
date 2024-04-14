package com.pawga.controllers

import com.pawga.domain.model.Dictionary
import com.pawga.domain.model.DictionaryValue
import com.pawga.domain.service.ReactorStorageChildrenService
import com.pawga.domain.service.ReactorStorageService
import com.pawga.storage.service.DictionaryService
import com.pawga.storage.service.DictionaryValueService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpHeaders
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

@Controller("/api/v1/dictionary")
open class DictionaryController(
    private val dictionaryService: ReactorStorageService<Dictionary, Long>,
    private val dictionaryValueService: ReactorStorageChildrenService<DictionaryValue, Long>
) {

    @Get("/list-pageable")
    open fun list(@Valid pageable: Pageable): Mono<Page<Dictionary>> = dictionaryService.findAll(pageable)

    @Get("/list")
    fun findAll(): Flux<Dictionary> = dictionaryService.findAll()

    @Get("/list-with-values")
    fun getAll(): List<Dictionary> {
        return dictionaryService.findAll().collectList().block()?.map(::readDictionary) ?: emptyList()
    }

    @Post
    fun save(@NotBlank name: String): Mono<HttpResponse<Dictionary>> {
        return dictionaryService.save(Dictionary(name = name)).mapNotNull {
            createDictionary(it!!)
        }
    }

    @Get("/{id}")
    fun get(id: Long): Mono<Dictionary?> = dictionaryService.get(id)

    @Put
    fun update(@Valid @Body obj: Dictionary): Mono<HttpResponse<*>> {
        return dictionaryService.update(obj).thenReturn(
            HttpResponse
                .noContent<Any>()
                .header(HttpHeaders.LOCATION, location(obj.id).path)
        )
    }

    @Delete("/{id}")
    fun delete(id: Long): Mono<Long?> = dictionaryService.delete(id)

    private fun createDictionary(dictionary: Dictionary): MutableHttpResponse<Dictionary> {
        return HttpResponse
            .created(dictionary)
            .headers { headers: MutableHttpHeaders ->
                headers.location(location(dictionary))
            }
    }

    private fun readDictionary(dictionary: Dictionary): Dictionary {
        if (dictionary.id == null) return dictionary
        val values = dictionaryValueService.findAllByDictionaryId(dictionary.id).collectList().block()
        if (values == null || values.isEmpty()) return dictionary
        return dictionary.copy(
            values = values.toList()
        )
    }

    private fun location(id: Long?): URI {
        return URI.create("/api/v1/dictionary/$id")
    }

    private fun location(dictionary: Dictionary): URI {
        return location(dictionary.id)
    }
}