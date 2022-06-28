package com.example.domain.usecases

import com.example.domain.models.GameDomain
import com.example.domain.repositories.GameRepository

interface UpdateGameUseCase {
    suspend fun execute(game: GameDomain)
}

class UpdateGameUseCaseImpl(
    private val gameRepository: GameRepository
) : UpdateGameUseCase {

    override suspend fun execute(game: GameDomain) {
        gameRepository.update(game)
    }
}