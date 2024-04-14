package com.pawga.storage.service

import com.pawga.domain.model.DictionaryValue
import com.pawga.domain.service.ReactorStorageService
import com.pawga.domain.service.ReactorStorageChildrenService
import com.pawga.storage.extensions.toDb
import com.pawga.storage.extensions.toResponse
import com.pawga.storage.repository.DictionaryValueRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Created by pawga777
 */
@Singleton
class DictionaryValueService(private val repository: DictionaryValueRepository) :
    ReactorStorageService<DictionaryValue, Long>,
    ReactorStorageChildrenService<DictionaryValue, Long>
{

    override fun findAll(pageable: Pageable): Mono<Page<DictionaryValue>> =
        repository.findAll(pageable).map {
            it.map { itDict ->
                itDict.toResponse()
            }
        }

    override fun findAllByDictionaryId(id: Long): Flux<DictionaryValue> {
        return repository.findAllByDictionaryId(id).map { it.toResponse() }
    }

    override fun findAll(): Flux<DictionaryValue> = repository.findAll().map { it.toResponse() }
    override fun save(obj: DictionaryValue): Mono<DictionaryValue?> = repository.save(obj.toDb()).mapNotNull { it.toResponse() }
    override fun get(id: Long): Mono<DictionaryValue?> = repository.findById(id).map { it.toResponse() }
    override fun update(obj: DictionaryValue): Mono<DictionaryValue> = repository.update(obj.toDb()).map { it.toResponse() }
    override fun delete(id: Long): Mono<Long?> = repository.deleteById(id)
}