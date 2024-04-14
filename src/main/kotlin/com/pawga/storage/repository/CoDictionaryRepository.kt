package com.pawga.storage.repository

import com.pawga.storage.data.DictionaryDb
import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutinePageableCrudRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by pawga777
 */

@R2dbcRepository(dialect = Dialect.POSTGRES)
abstract class CoDictionaryRepository : CoroutinePageableCrudRepository<DictionaryDb, Long> {
    @Query("SELECT * FROM public.dictionary where id = :id;")
    abstract fun findByDictionaryId(id: Long): Flow<DictionaryDb>
}