package com.example.domain.usecases

import com.example.domain.models.GameDomain
import com.example.domain.repositories.GameRepository

interface GetGamesUseCase {
    suspend fun execute(): List<GameDomain>
}

class GetGamesUsesCaseImpl(private val gameRepository: GameRepository) : GetGamesUseCase {

    override suspend fun execute(): List<GameDomain> {
        return gameRepository.get()
    }
}