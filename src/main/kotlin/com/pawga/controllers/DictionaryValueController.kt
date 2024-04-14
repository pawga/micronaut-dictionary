package com.pawga.controllers

import com.pawga.domain.model.ShortDictionaryValue
import com.pawga.domain.model.DictionaryValue
import com.pawga.domain.service.ReactorStorageService
import com.pawga.storage.extensions.toResponse
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

/**
 * Created by pawga777
 */
@Controller("/api/v1/dictionary-value")
class DictionaryValueController(private val dictionaryService: ReactorStorageService<DictionaryValue, Long>) {
    @Get("/list-pageable")
    open fun list(@Valid pageable: Pageable): Mono<Page<DictionaryValue>> = dictionaryService.findAll(pageable)

    @Get("/list")
    fun findAll(): Flux<DictionaryValue> = dictionaryService.findAll()

    @Post
    fun save(@NotBlank @Body value: ShortDictionaryValue): Mono<HttpResponse<DictionaryValue>> {
        return dictionaryService.save(value.toResponse()).mapNotNull {
            createDictionaryValue(it!!)
        }
    }

    @Get("/{id}")
    fun get(id: Long): Mono<DictionaryValue?> = dictionaryService.get(id)

    @Put
    fun update(@Valid @Body obj: DictionaryValue): Mono<HttpResponse<*>> {
        return dictionaryService.update(obj).thenReturn(
            HttpResponse
                .noContent<Any>()
                .header(HttpHeaders.LOCATION, location(obj.id).path)
        )
    }

    @Delete("/{id}")
    fun delete(id: Long): Mono<Long?> = dictionaryService.delete(id)

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