package com.example.terpila.adapters

import com.example.terpila.models.Game


interface ItemGameListener {

    fun onPlayButtonClickListener(game: Game)
    fun onDifficultyChangedListener(newDifficulty: Int, game: Game)
}