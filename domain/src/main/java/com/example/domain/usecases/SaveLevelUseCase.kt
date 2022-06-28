package com.example.domain.usecases

import com.example.domain.repositories.PreferencesRepository

interface SaveLevelUseCase {
    suspend fun execute(level: Int)
}

class SaveLevelUseCaseImpl(
    private val preferencesRepository: PreferencesRepository
) : SaveLevelUseCase {

    override suspend fun execute(level: Int) {
        preferencesRepository.saveLevel(level)
    }
}