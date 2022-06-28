package com.example.domain.usecases

import com.example.domain.repositories.PreferencesRepository

interface SaveProgressUseCase {
    suspend fun execute(progress: Int)
}

class SaveProgressUseCaseImpl(
    private val preferencesRepository: PreferencesRepository
) : SaveProgressUseCase {

    override suspend fun execute(progress: Int) {
        preferencesRepository.saveProgress(progress)

    }
}