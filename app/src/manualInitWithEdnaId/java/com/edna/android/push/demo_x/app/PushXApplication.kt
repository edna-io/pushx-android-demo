package com.edna.android.push.demo_x.app

import androidx.work.Configuration
import com.edna.android.push.demo_x.BuildConfig
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

        val ednaAppId = when (BuildConfig.BUILD_TYPE) {
            "debugRegressionStend" -> "eyJ0ZW5hbnRVdWlkIjoiYjY3OGFiZDMtMjA5NS00ZDNmLWJmYWYtNGMxM2E3ZGM1MTU5IiwicHJvdmlkZXJVaWQiOiJZMjl0TG1Wa2JtRXVZVzVrY205cFpDNXdkWE5vTG1SbGJXOWZlRjlpTmpjNFlXSmtNeTB5TURrMUxUUmtNMll0WW1aaFppMDBZekV6WVRka1l6VXhOVGs9In0="
            else -> "eyJ0ZW5hbnRVdWlkIjoiNWY0ZDVmMGItZDI3Ny00YWNlLTgyNjUtOTFmNjk5OWQ3NjUzIiwicHJvdmlkZXJVaWQiOiJZMjl0TG1Wa2JtRXVZVzVrY205cFpDNXdkWE5vTG1SbGJXOWZlRjgxWmpSa05XWXdZaTFrTWpjM0xUUmhZMlV0T0RJMk5TMDVNV1kyT1RrNVpEYzJOVE09In0="
        }
        PushX.initialize(applicationContext, ednaAppId)

        RuStorePushClient.init(
            application = this,
            projectId = "zpw73i3UZw1JgSKi1XNKjaJDAnaAQOhc",
            logger = DefaultLogger()
        )

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
