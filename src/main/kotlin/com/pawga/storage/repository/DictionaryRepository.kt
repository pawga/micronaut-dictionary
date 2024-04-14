package com.pawga.storage.repository

import com.pawga.storage.data.DictionaryDb
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.reactive.ReactorPageableRepository

/**
 * Created by pawga777
 */
@R2dbcRepository(dialect = Dialect.POSTGRES)
abstract class DictionaryRepository : ReactorPageableRepository<DictionaryDb, Long>