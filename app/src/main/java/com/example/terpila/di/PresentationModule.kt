package com.example.terpila.di

import com.example.terpila.viewmodels.ChooserGameFragViewModel
import com.example.terpila.viewmodels.GamesResultFragViewModel
import com.example.terpila.viewmodels.MenuFragViewModel
import com.example.terpila.viewmodels.RewardDialogFragViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        MenuFragViewModel(
            getCoinsCountUseCase = get(),
            getLevelUseCase = get(),
            getProgressUseCase = get(),
            saveLevelUseCase = get(),
            saveProgressUseCase = get()
        )
    }

    viewModel {
        ChooserGameFragViewModel(
            getGamesUseCase = get(),
            saveGamesUseCase = get(),
            updateGameUseCase = get(),
            getCoinsCountUseCase = get(),
            saveCoinsCountUseCase = get(),
        )
    }

    viewModel {
        GamesResultFragViewModel(
            getCoinsCountUseCase = get(),
            saveCoinsCountUseCase = get(),
            getRecordUseCase = get(),
            saveRecordUseCase = get()
        )
    }

    viewModel {
        RewardDialogFragViewModel(
            getProgressUseCase = get(),
            saveProgressUseCase = get(),
            getLevelUseCase = get(),
            saveLevelUseCase = get()
        )
    }
}