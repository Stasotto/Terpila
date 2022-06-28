package com.example.data.di


import androidx.room.Room
import com.example.data.storage.DatabaseStorage
import com.example.data.storage.DatabaseStorageImpl
import com.example.data.storage.SharedPreferences
import com.example.data.storage.SharedPreferencesImpl
import com.example.data.storage.room.AppDatabase
import org.koin.dsl.module

val dataModule = module {

    single<SharedPreferences> {
        SharedPreferencesImpl(context = get())
    }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "games"
        ).build()
    }

    single {
        get<AppDatabase>().getGameDao()
    }

    single<DatabaseStorage> {
        DatabaseStorageImpl(gameDao = get())
    }

}