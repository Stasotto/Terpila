package com.example.data.storage

import com.example.data.storage.room.GameEntity

interface DatabaseStorage {

    suspend fun get(): List<GameEntity>

    suspend fun update(gameEntity: GameEntity)

    suspend fun save(vararg entity: GameEntity)
}