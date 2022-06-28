package com.example.domain.usecases

import com.example.domain.repositories.PreferencesRepository

interface GetProgressUseCase {
    suspend fun execute(): Int
}
class GetProgressUseCaseImpl(
    private val preferencesRepository: PreferencesRepository
) : GetProgressUseCase {

    override suspend fun execute(): Int {
        return preferencesRepository.getProgress()

    }
}