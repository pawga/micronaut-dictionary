package com.pawga.storage.repository

import com.pawga.storage.data.DictionaryValueDb
import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.reactive.ReactorPageableRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Created by pawga777
 */
@R2dbcRepository(dialect = Dialect.POSTGRES)
abstract class DictionaryValueRepository : ReactorPageableRepository<DictionaryValueDb, Long> {

    @Query("SELECT * FROM public.dictionary_value where dictionary_id = :id;")
    abstract fun findAllByDictionaryId(id: Long): Flux<DictionaryValueDb>

    @Query("SELECT * FROM public.dictionary_value where dictionary_id = :dictionaryId and code = :code;")
    abstract fun findByCodeAndDictionaryId(
        code: String,
        dictionaryId: Long,
    ): Mono<DictionaryValueDb>

    @Query(
        "insert into dictionary_value(code, value, dictionary_id) values(:code, :value, :dictionaryId) RETURNING id;",
    )
    abstract fun createValue(
        code: String,
        value: String,
        dictionaryId: Long,
    ): Long
}