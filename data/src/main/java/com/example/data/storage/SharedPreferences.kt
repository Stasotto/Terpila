package com.example.data.storage

interface SharedPreferences {

    suspend fun getCoins(): Int
    suspend fun saveCoins(coins: Int)

    suspend fun getRecord(className: String): Int
    suspend fun saveRecord(className: String, record: Int)

    suspend fun getProgress(): Int
    suspend fun saveProgress(progress: Int)

    suspend fun getLevel(): Int
    suspend fun saveLevel(level: Int)
}