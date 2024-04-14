package com.pawga.domain.service

import kotlinx.coroutines.flow.Flow

/**
 * Created by pawga777 on 05.04.2024 16:33
 */
interface CoStorageChildrenService<C, K> {
    suspend fun findAllByDictionaryId(id: K): Flow<C>
}