package com.pawga.storage.service

import com.pawga.domain.model.Dictionary
import com.pawga.domain.service.CoStorageService
import com.pawga.storage.extensions.toDb
import com.pawga.storage.extensions.toResponse
import com.pawga.storage.repository.CoDictionaryRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * Created by pawga777
 */

@Singleton
class CoDictionaryService(private val repository: CoDictionaryRepository) : CoStorageService<Dictionary, Long> {

    override fun findAll(): Flow<Dictionary> = repository.findAll().map { it.toResponse() }
    override suspend fun findAll(pageable: Pageable): Page<Dictionary> = repository.findAll(pageable).map {
        it.toResponse()
    }
    override suspend fun save(obj: Dictionary): Dictionary = repository.save(obj.toDb()).toResponse()
//    override suspend fun get(id: Long): Dictionary? = repository.findByDictionaryId(id).firstOrNull()?.toResponse()
    override suspend fun get(id: Long): Dictionary? = repository.findById(id)?.toResponse()
    override suspend fun update(obj: Dictionary): Dictionary = repository.update(obj.toDb()).toResponse()
    override suspend fun delete(id: Long): Long = repository.deleteById(id).toLong()
}