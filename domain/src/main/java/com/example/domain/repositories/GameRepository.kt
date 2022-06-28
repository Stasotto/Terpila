package com.example.domain.repositories

import com.example.domain.models.GameDomain

interface GameRepository {

    suspend fun save(vararg game: GameDomain)

    suspend fun update(game: GameDomain)

    suspend fun get(): List<GameDomain>
}