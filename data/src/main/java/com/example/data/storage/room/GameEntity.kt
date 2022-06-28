package com.example.data.storage.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.GameDifficultyDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "games")
data class GameEntity(
    @ColumnInfo
    val name: String,
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val isEnable: Boolean,
    @ColumnInfo
    val currentDifficulty: Int,
    @ColumnInfo
    val levels: String
)

internal fun List<GameDifficultyDomain>.toGsonString(): String = Gson().toJson(this)

internal fun String.fromGsonToListDifficulties(): List<GameDifficultyDomain> {
    val type: Type = object : TypeToken<List<GameDifficultyDomain>>() {}.type
    val gson = Gson()
    return gson.fromJson(this, type)
}