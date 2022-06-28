package com.example.terpila.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.*
import kotlinx.coroutines.launch
import java.util.*

class MenuFragViewModel(
    private val getCoinsCountUseCase: GetCoinsCountUseCase,
    private val getProgressUseCase: GetProgressUseCase,
    private val getLevelUseCase: GetLevelUseCase,
    private val saveProgressUseCase: SaveProgressUseCase,
    private val saveLevelUseCase: SaveLevelUseCase
) : ViewModel() {

    private val _coinsCount  = MutableLiveData<Int>()
    val coinsCount: LiveData<Int> get() = _coinsCount

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress

    private val _level = MutableLiveData<Int>()
    val level: LiveData<Int> get() = _level

    fun updateData() {
        loadLevel()
        loadCoins()
        loadProgress()
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _coinsCount.value = getCoinsCountUseCase.execute()
        }
    }

   private fun loadProgress() {
        viewModelScope.launch {
            _progress.value = getProgressUseCase.execute()
        }
    }

    private fun loadLevel() {
        viewModelScope.launch {
            _level.value = getLevelUseCase.execute()
        }
    }

    fun saveProgress(progress: Int) {
        viewModelScope.launch {
            saveProgressUseCase.execute(progress)
        }
    }

    fun saveLevel(level: Int) {
        viewModelScope.launch {
            saveLevelUseCase.execute(level)
        }
    }
}