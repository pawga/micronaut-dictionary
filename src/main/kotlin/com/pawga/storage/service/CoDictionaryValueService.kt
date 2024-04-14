package com.pawga.storage.service

import com.pawga.domain.model.DictionaryValue
import com.pawga.domain.service.CoStorageChildrenService
import com.pawga.domain.service.CoStorageService
import com.pawga.storage.extensions.toDb
import com.pawga.storage.extensions.toResponse
import com.pawga.storage.repository.CoDictionaryValueRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by pawga777
 */

@Singleton
class CoDictionaryValueService(private val repository: CoDictionaryValueRepository) :
    CoStorageService<DictionaryValue, Long>, CoStorageChildrenService<DictionaryValue, Long> {

    override fun findAll(): Flow<DictionaryValue> = repository.findAll().map { it.toResponse() }
    override suspend fun findAll(pageable: Pageable): Page<DictionaryValue> = repository.findAll(pageable).map {
        it.toResponse()
    }
    override suspend fun delete(id: Long): Long = repository.deleteById(id).toLong()
    override suspend fun update(obj: DictionaryValue): DictionaryValue = repository.update(obj.toDb()).toResponse()
    override suspend fun get(id: Long): DictionaryValue? = repository.findById(id)?.toResponse()
    override suspend fun save(obj: DictionaryValue): DictionaryValue = repository.save(obj.toDb()).toResponse()

    override suspend fun findAllByDictionaryId(id: Long): Flow<DictionaryValue> {
        return repository.findAllByDictionaryId(id).map { it.toResponse() }
    }
}