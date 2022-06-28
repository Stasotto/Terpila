package com.example.domain.usecases

import com.example.domain.repositories.PreferencesRepository

interface GetRecordUseCase {
    suspend fun execute(className: String): Int
}

class GetRecordUseCaseImpl(
    private val preferencesRepository: PreferencesRepository
) : GetRecordUseCase {

    override suspend fun execute(className: String): Int {
        return preferencesRepository.getRecord(className)
    }
}