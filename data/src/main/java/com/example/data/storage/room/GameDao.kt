package com.example.data.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameDao {

    @Query("SELECT * FROM games")
    fun getGames(): List<GameEntity>

    @Insert
    fun saveGames(vararg gameEntity: GameEntity)

    @Update
    fun updateGame(game: GameEntity)
}