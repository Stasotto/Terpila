package com.example.terpila.di

import com.example.data.GameRepositoryImpl
import com.example.data.PreferencesRepositoryImpl
import com.example.domain.repositories.GameRepository
import com.example.domain.repositories.PreferencesRepository
import com.example.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    single<GetCoinsCountUseCase> {
        GetCoinsCountUseCaseImpl(repository = get())
    }
    single<PreferencesRepository> {
        PreferencesRepositoryImpl(sharedPreferences = get())
    }

    single<GameRepository> {
        GameRepositoryImpl(databaseStorage = get())
    }

    single<GetGamesUseCase> {
        GetGamesUsesCaseImpl(gameRepository = get())
    }

    single<UpdateGameUseCase> {
        UpdateGameUseCaseImpl(gameRepository = get())
    }

    single<SaveGamesUseCase> {
        SaveGamesUseCaseImpl(gameRepository = get())
    }

    single<SaveCoinsCountUseCase> {
        SaveCoinsCountUseCaseImpl(preferencesRepository = get())
    }

    single<SaveRecordUseCase> {
        SaveRecordUseCaseImpl(preferencesRepository = get())
    }

    single<GetRecordUseCase> {
        GetRecordUseCaseImpl(preferencesRepository = get())
    }

    single<GetLevelUseCase>{
        GetLevelUseCaseImpl(preferencesRepository = get())
    }

    single<SaveLevelUseCase> {
        SaveLevelUseCaseImpl(preferencesRepository = get())
    }

    single<GetProgressUseCase> {
        GetProgressUseCaseImpl(preferencesRepository = get())
    }

    single<SaveProgressUseCase> {
        SaveProgressUseCaseImpl(preferencesRepository = get())
    }

}