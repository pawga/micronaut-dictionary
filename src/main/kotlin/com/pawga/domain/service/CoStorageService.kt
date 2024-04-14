package com.pawga.domain.service

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import kotlinx.coroutines.flow.Flow

interface CoStorageService<M, K> {
    fun findAll(): Flow<M>
    suspend fun findAll(pageable: Pageable): Page<M>
    suspend fun save(obj: M): M
    suspend fun get(id: K): M?
    suspend fun update(obj: M): M
    suspend fun delete(id: K): K
}
