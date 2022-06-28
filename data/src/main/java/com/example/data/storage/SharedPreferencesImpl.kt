package com.example.data.storage

import android.content.Context

class SharedPreferencesImpl(private val context: Context) : SharedPreferences {
    companion object {
        private const val SHARED_PREF_NAME = "MenuFragment"
        private const val COINS_COUNT_KEY = "Coins"
        private const val DEFAULT_VALUE = 0
        private const val PROGRESS_KEY = "Progress key"
        private const val LEVEL_KEY = "Level key"
    }

    private val sharedPref by lazy {
        context.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    override suspend fun getCoins(): Int {
        return sharedPref.getInt(COINS_COUNT_KEY, DEFAULT_VALUE)
    }

    override suspend fun saveCoins(coins: Int) {
        sharedPref.edit().putInt(COINS_COUNT_KEY, coins).apply()
    }

    override suspend fun getRecord(className: String): Int {
        return sharedPref.getInt(className, DEFAULT_VALUE)
    }

    override suspend fun saveRecord(className: String, record: Int) {
        sharedPref.edit().putInt(className,record).apply()
    }

    override suspend fun getProgress(): Int {
        return sharedPref.getInt(PROGRESS_KEY, DEFAULT_VALUE)
    }

    override suspend fun saveProgress(progress: Int) {
     sharedPref.edit().putInt(PROGRESS_KEY,progress).apply()
    }

    override suspend fun getLevel(): Int {
        return sharedPref.getInt(LEVEL_KEY, DEFAULT_VALUE)
    }

    override suspend fun saveLevel(level: Int) {
        sharedPref.edit().putInt(LEVEL_KEY, level).apply()
    }


}