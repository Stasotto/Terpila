package com.example.domain.usecases

import com.example.domain.repositories.PreferencesRepository

interface GetLevelUseCase {
    suspend fun execute(): Int
}
class GetLevelUseCaseImpl(
    private val preferencesRepository: PreferencesRepository
) : GetLevelUseCase{

    override suspend fun execute(): Int {
       return preferencesRepository.getLevel()
    }
}