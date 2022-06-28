package com.example.data

import com.example.data.storage.room.GameEntity
import com.example.data.storage.room.fromGsonToListDifficulties
import com.example.data.storage.room.toGsonString
import com.example.domain.models.GameDomain

fun GameDomain.toGameEntity() = GameEntity(
    name = name,
    id = id,
    isEnable = isEnable,
    currentDifficulty = currentDifficulty,
    levels = levels.toGsonString()
)

fun GameEntity.toGameDomain() = GameDomain(
    name = name,
    id = id,
    isEnable = isEnable,
    currentDifficulty = currentDifficulty,
    levels = levels.fromGsonToListDifficulties()
)