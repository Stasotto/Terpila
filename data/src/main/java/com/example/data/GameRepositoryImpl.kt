package com.example.data

import com.example.data.storage.DatabaseStorage
import com.example.domain.models.GameDomain
import com.example.domain.repositories.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepositoryImpl(
    private val databaseStorage: DatabaseStorage
) : GameRepository {

    override suspend fun save(vararg game: GameDomain) {
        withContext(Dispatchers.IO) {
            databaseStorage.save(*game.map { gameDomain ->
                gameDomain.toGameEntity()
            }.toTypedArray())
        }
    }

    override suspend fun update(game: GameDomain) {
        withContext(Dispatchers.IO) {
            databaseStorage.update(game.toGameEntity())
        }
    }

    override suspend fun get(): List<GameDomain> {
        return withContext(Dispatchers.IO) {
            databaseStorage.get().map { gameEntity ->
                gameEntity.toGameDomain()
            }
        }
    }
}