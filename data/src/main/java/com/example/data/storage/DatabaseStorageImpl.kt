package com.example.data.storage

import com.example.data.storage.room.GameDao
import com.example.data.storage.room.GameEntity

class DatabaseStorageImpl(private val gameDao: GameDao) : DatabaseStorage {

    override suspend fun get(): List<GameEntity> {
           return gameDao.getGames()
    }

    override suspend fun update(gameEntity: GameEntity) {
            gameDao.updateGame(gameEntity)
    }

    override suspend fun save(vararg entity: GameEntity) {
            gameDao.saveGames(*entity)
    }
}