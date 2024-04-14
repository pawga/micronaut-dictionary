package com.pawga.domain.service

import reactor.core.publisher.Flux

/**
 * Created by pawga777
 */
interface ReactorStorageChildrenService<C, K> {
    fun findAllByDictionaryId(id: K): Flux<C>
}