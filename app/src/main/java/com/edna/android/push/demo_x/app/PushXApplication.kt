package com.edna.android.push.demo_x.app

import androidx.startup.AppInitializer
import androidx.work.Configuration
import com.edna.android.push.demo_x.data.PushRepository
import com.edna.android.push.demo_x.di.DaggerApplicationComponent
import com.edna.android.push.demo_x.pushprocessing.MainDelegatingWorkerFactory
import com.edna.android.push_x.PushXInitializer
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import javax.inject.Provider

class PushXApplication : DaggerApplication(), Configuration.Provider, HasAndroidInjector {

    @Inject
    lateinit var pushRepository: Provider<PushRepository>

    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(applicationContext)
            .initializeComponent(PushXInitializer::class.java)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(MainDelegatingWorkerFactory(pushRepository))
            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }

}
