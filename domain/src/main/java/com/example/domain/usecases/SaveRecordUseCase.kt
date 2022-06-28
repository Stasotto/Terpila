package com.example.domain.usecases

import com.example.domain.repositories.PreferencesRepository

interface SaveRecordUseCase {
    suspend fun execute(className: String, record: Int)
}
class SaveRecordUseCaseImpl(
    private val preferencesRepository: PreferencesRepository
) : SaveRecordUseCase {

    override suspend fun execute(className: String, record: Int) {
        preferencesRepository.saveRecord(className,record)
    }
}