package com.example.domain.usecases

import com.example.domain.repositories.PreferencesRepository

interface SaveCoinsCountUseCase {
    suspend fun execute(coins: Int)
}
class SaveCoinsCountUseCaseImpl(
    private val preferencesRepository: PreferencesRepository
) : SaveCoinsCountUseCase {

    override suspend fun execute(coins: Int) {
     preferencesRepository.saveCoins(coins)
    }
}