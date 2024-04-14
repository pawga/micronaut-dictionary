package com.pawga.domain.service

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReactorStorageService<M, K> {
    fun findAll(): Flux<M>
    fun findAll(pageable: Pageable): Mono<Page<M>>
    fun save(obj: M): Mono<M?>
    fun get(id: K): Mono<M?>
    fun update(obj: M): Mono<M>
    fun delete(id: K): Mono<K?>
}
