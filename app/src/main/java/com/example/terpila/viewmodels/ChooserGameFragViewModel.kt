package com.example.terpila.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.*
import com.example.terpila.models.Game
import com.example.terpila.toGame
import com.example.terpila.toGameDomain
import kotlinx.coroutines.launch

class ChooserGameFragViewModel(
    private val getGamesUseCase: GetGamesUseCase,
    private val saveGamesUseCase: SaveGamesUseCase,
    private val updateGameUseCase: UpdateGameUseCase,
    private val getCoinsCountUseCase: GetCoinsCountUseCase,
    private val saveCoinsCountUseCase: SaveCoinsCountUseCase
) : ViewModel() {

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games

    private val _coins = MutableLiveData<Int>()
    val coins: LiveData<Int> get() = _coins

    init {
        loadGames()
        loadCoins()
    }

    private fun loadGames() {
        viewModelScope.launch {
            val result = getGamesUseCase.execute().map { gameDomain ->
                gameDomain.toGame()
            }
            Log.d("MyLogResult", result.toString())
            _games.value = if (result.isEmpty()) {
                val games = Game.getDefaultGames()
//                saveGames(games)
                games
            } else {
                result
            }
        }
    }

    fun changeDifficultyOfGame(newDifficulty: Int, game: Game) {
        val gameList = _games.value?.toMutableList() ?: return
        val index = gameList.indexOf(game)
        if (index == -1) return
        gameList[index] = gameList[index].copy(currentDifficulty = newDifficulty)
        _games.value = gameList
        updateGame(gameList[index])
        Log.d("MyLog", "changed")
    }

    private fun saveGames(gameList: List<Game>) {
        viewModelScope.launch {
            saveGamesUseCase.execute(*gameList.map {
                it.toGameDomain()
            }.toTypedArray())
        }
    }

    private fun updateGame(game: Game) {
        viewModelScope.launch {
            updateGameUseCase.execute(game.toGameDomain())
        }

    }

    private fun loadCoins() {
        viewModelScope.launch {
            _coins.value = getCoinsCountUseCase.execute()
        }
    }

    fun saveCoins(coins: Int) {
        viewModelScope.launch {
            saveCoinsCountUseCase.execute(coins)
        }
    }
}