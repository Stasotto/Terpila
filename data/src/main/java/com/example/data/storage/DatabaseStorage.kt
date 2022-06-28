package com.example.data.storage

import androidx.recyclerview.widget.ListAdapter
import com.example.data.storage.room.GameEntity

interface DatabaseStorage {

   suspend fun get(): List<GameEntity>

   suspend fun update(gameEntity: GameEntity)

   suspend fun save(vararg entity: GameEntity)
}