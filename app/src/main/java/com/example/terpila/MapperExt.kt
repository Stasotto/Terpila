package com.example.terpila

import com.example.domain.models.GameDifficultyDomain
import com.example.domain.models.GameDomain
import com.example.terpila.models.Game
import com.example.terpila.models.GameDifficulty

fun GameDomain.toGame() = Game(
    name = name,
    id = id,
    isEnable = isEnable,
    currentDifficulty = currentDifficulty,
    levels = levels.map { gameDifficultyDomain ->
        gameDifficultyDomain.toGameDifficulty()
    }
)

fun Game.toGameDomain() = GameDomain(
    name = name,
    id = id,
    isEnable = isEnable,
    currentDifficulty = currentDifficulty,
    levels = levels.map { gameDifficulty ->
        gameDifficulty.toGameDifficultyDomain()
    }

)
fun GameDifficulty.toGameDifficultyDomain() = GameDifficultyDomain(
    isEnable = isEnable,
    text = text,
    value = value
)

fun GameDifficultyDomain.toGameDifficulty() = GameDifficulty(
    isEnable = isEnable,
    text = text,
    value = value
)