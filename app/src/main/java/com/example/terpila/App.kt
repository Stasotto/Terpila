package com.example.terpila

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.example.data.di.dataModule
import com.example.terpila.di.domainModule
import com.example.terpila.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        registerActivityLifecycleCallbacks(this)
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d("MyLog", "onCreate")
    }


    override fun onActivityStarted(activity: Activity) {
        Log.d("MyLog", "onStart")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d("MyLog", "onResume")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d("MyLog", "onPause")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d("MyLog", "onStop")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d("MyLog", "onSaveInstance")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d("MyLog", "onDestroy")
    }
}