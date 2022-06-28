package com.example.terpila.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetCoinsCountUseCase
import com.example.domain.usecases.GetRecordUseCase
import com.example.domain.usecases.SaveCoinsCountUseCase
import com.example.domain.usecases.SaveRecordUseCase
import kotlinx.coroutines.launch

class GamesResultFragViewModel(
    private val getCoinsCountUseCase: GetCoinsCountUseCase,
    private val saveCoinsCountUseCase: SaveCoinsCountUseCase,
    private val getRecordUseCase: GetRecordUseCase,
    private val saveRecordUseCase: SaveRecordUseCase
) : ViewModel() {

    private val _coinsCount = MutableLiveData<Int>()
    val coinsCount: LiveData<Int> get() = _coinsCount

    private val _record = MutableLiveData<Int>()
    val record: LiveData<Int> get() = _record

    fun loadCoinsAndRecord(className: String) {
        viewModelScope.launch {
            _coinsCount.value = getCoinsCountUseCase.execute()
            _record.value = getRecordUseCase.execute(className)
        }
    }

    fun saveCoinsCount(coins: Int) {
        viewModelScope.launch {
            saveCoinsCountUseCase.execute(coins)
        }
    }

    fun saveRecord(className: String, record: Int) {
        viewModelScope.launch {
            saveRecordUseCase.execute(className, record)
        }
    }
}