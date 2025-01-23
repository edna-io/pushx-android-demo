package com.edna.android.push.demo_x.app

import androidx.work.Configuration
import com.edna.android.push.demo_x.data.PushRepository
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.di.DaggerApplicationComponent
import com.edna.android.push.demo_x.pushprocessing.DemoNewPushMessageHandler
import com.edna.android.push.demo_x.pushprocessing.MainDelegatingWorkerFactory
import com.edna.android.push_x.PushX
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import javax.inject.Provider
import ru.rustore.sdk.pushclient.RuStorePushClient
import ru.rustore.sdk.pushclient.common.logger.DefaultLogger

class PushXApplication : DaggerApplication(), Configuration.Provider, HasAndroidInjector {

    @Inject
    lateinit var pushRepository: Provider<PushRepository>

    @Inject
    lateinit var preferenceStore: PreferenceStore

    override fun onCreate() {
        super.onCreate()

        PushX.initialize(applicationContext)
        PushX.addEventHandler(DemoNewPushMessageHandler(applicationContext, preferenceStore))
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
