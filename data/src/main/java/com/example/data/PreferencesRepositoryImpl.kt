package com.example.data

import com.example.data.storage.SharedPreferences
import com.example.domain.repositories.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PreferencesRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : PreferencesRepository {

    override suspend fun getCoins(): Int {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getCoins()
        }
    }

    override suspend fun saveCoins(coins: Int) {
        return withContext(Dispatchers.IO) {
            sharedPreferences.saveCoins(coins)
        }
    }

    override suspend fun getRecord(className: String): Int {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getRecord(className)
        }
    }

    override suspend fun saveRecord(className: String, record: Int) {
        withContext(Dispatchers.IO) {
            sharedPreferences.saveRecord(className, record)
        }
    }

    override suspend fun getProgress(): Int {
       return withContext(Dispatchers.IO) {
            sharedPreferences.getProgress()
        }
    }

    override suspend fun saveProgress(progress: Int) {
        withContext(Dispatchers.IO) {
            sharedPreferences.saveProgress(progress)
        }
    }

    override suspend fun getLevel(): Int {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getLevel()
        }
    }

    override suspend fun saveLevel(level: Int) {
        withContext(Dispatchers.IO) {
            sharedPreferences.saveLevel(level)
        }
    }
}