package com.example.terpila.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetLevelUseCase
import com.example.domain.usecases.GetProgressUseCase
import com.example.domain.usecases.SaveLevelUseCase
import com.example.domain.usecases.SaveProgressUseCase
import kotlinx.coroutines.launch

class RewardDialogFragViewModel(
    private val getProgressUseCase: GetProgressUseCase,
    private val getLevelUseCase: GetLevelUseCase,
    private val saveProgressUseCase: SaveProgressUseCase,
    private val saveLevelUseCase: SaveLevelUseCase
) : ViewModel() {

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress

    private val _level = MutableLiveData<Int>()
    val level: LiveData<Int> get() = _level

    init {
        loadLevel()
        loadProgress()
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
}