package com.pawga.storage.extensions

import com.pawga.domain.model.ShortDictionaryValue
import com.pawga.domain.model.Dictionary
import com.pawga.domain.model.DictionaryValue
import com.pawga.storage.data.DictionaryDb
import com.pawga.storage.data.DictionaryValueDb

/**
 * Created by pawga777
 */

fun Dictionary.toDb(): DictionaryDb =
    DictionaryDb(
        id = id,
        name = name,
    )

fun DictionaryDb.toResponse(): Dictionary =
    Dictionary(
        id = this.id,
        name = name,
    )

fun DictionaryValue.toDb(): DictionaryValueDb {
    return DictionaryValueDb(
        if (id == 0L) null else id,
        code = code,
        value = value,
        dictionaryId = parentId,
    )
}

fun ShortDictionaryValue.toResponse(): DictionaryValue {
    return DictionaryValue(
        id = 0L,
        code = code,
        value = value,
        parentId = parentId,
    )
}

fun DictionaryValueDb.toResponse(): DictionaryValue =
    DictionaryValue(
        id = this.id ?: 0L,
        parentId = this.dictionaryId,
        code = code,
        value = value,
    )
