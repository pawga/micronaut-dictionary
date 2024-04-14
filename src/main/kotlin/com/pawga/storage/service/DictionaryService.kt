package com.pawga.storage.service

import com.pawga.domain.model.Dictionary
import com.pawga.domain.service.ReactorStorageService
import com.pawga.storage.extensions.toDb
import com.pawga.storage.extensions.toResponse
import com.pawga.storage.repository.DictionaryRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Created by pawga777
 */
@Singleton
class DictionaryService(private val repository: DictionaryRepository) : ReactorStorageService<Dictionary, Long> {

    override fun findAll(pageable: Pageable): Mono<Page<Dictionary>> =
        repository.findAll(pageable).map {
            it.map { itDict ->
                itDict.toResponse()
            }
        }

    override fun findAll(): Flux<Dictionary> = repository.findAll().map { it.toResponse() }
    override fun save(obj: Dictionary): Mono<Dictionary?> = repository.save(obj.toDb()).mapNotNull { it.toResponse() }
    override fun get(id: Long): Mono<Dictionary?> = repository.findById(id).map { it.toResponse() }
    override fun update(obj: Dictionary): Mono<Dictionary> = repository.update(obj.toDb()).map { it.toResponse() }
    override fun delete(id: Long): Mono<Long?> = repository.deleteById(id)
}