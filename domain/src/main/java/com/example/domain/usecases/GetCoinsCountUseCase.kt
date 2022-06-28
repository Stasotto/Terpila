package com.example.domain.usecases

import com.example.domain.repositories.PreferencesRepository

interface GetCoinsCountUseCase {

    suspend fun execute(): Int
}

class GetCoinsCountUseCaseImpl(
    private val repository: PreferencesRepository
) : GetCoinsCountUseCase {

    override suspend fun execute(): Int {
        return repository.getCoins()

    }
}