package com.example.domain.models

data class GameDomain(
    val name: String,
    val id: Int,
    val isEnable: Boolean,
    val currentDifficulty: Int,
    val levels: List<GameDifficultyDomain>
) {
}