package com.example.terpila.models

import com.example.terpila.fragments.findacouplegame.FindACoupleGameFragment

data class Game(
    val name: String,
    val id: Int,
    val isEnable: Boolean,
    val currentDifficulty: Int,
    val levels: List<GameDifficulty>
) {
    companion object {
        fun getDefaultGames() = listOf(
            Game("Поймай крота",1,true,0, listOf(
                GameDifficulty(true,"Легко", 1000),
                GameDifficulty(false,"Ты сможешь", 800),
                GameDifficulty(false,"Тяжело", 500),
                GameDifficulty(false,"Кошмар", 300))),
            Game("Поймай монету", 2,true,0, listOf(
                GameDifficulty(true,"Легко", 800),
                GameDifficulty(false,"Ты сможешь", 500),
                GameDifficulty(false,"Тяжело", 300),
                GameDifficulty(false,"Кошмар", 100))),
            Game("Найди пару", 3,true,0,listOf(
                GameDifficulty(true,"Легко", FindACoupleGameFragment.EASY),
                GameDifficulty(false,"Ты сможешь", FindACoupleGameFragment.YOU_CAN_DO_IT),
                GameDifficulty(false,"Тяжело", FindACoupleGameFragment.HARD),
                GameDifficulty(false,"Кошмар", FindACoupleGameFragment.NIGHTMARE))),
            Game("Танки", 4,false,0, listOf(
                GameDifficulty(true,"Легко", 1000),
                GameDifficulty(false,"Ты сможешь", 800),
                GameDifficulty(false,"Тяжело", 500),
                GameDifficulty(false,"Кошмар", 300))),
            Game("Шутер", 5, false,0, listOf(
                GameDifficulty(true,"Легко", 1000),
                GameDifficulty(false,"Ты сможешь", 800),
                GameDifficulty(false,"Тяжело", 500),
                GameDifficulty(false,"Кошмар", 300)))
        )
    }
}
