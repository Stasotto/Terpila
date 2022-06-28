package com.example.domain.usecases

import com.example.domain.models.GameDomain
import com.example.domain.repositories.GameRepository

interface SaveGamesUseCase {
    suspend fun execute(vararg game: GameDomain)
}

class SaveGamesUseCaseImpl(
    private val gameRepository: GameRepository
) : SaveGamesUseCase {

    override suspend fun execute(vararg game: GameDomain) {
        gameRepository.save(*game)
    }
}