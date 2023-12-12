package com.edna.android.push.demo_x.app

import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.di.DaggerApplicationComponent
import com.edna.android.push.demo_x.pushprocessing.DemoNewPushMessageHandler
import com.edna.android.push_x.PushX
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject
import ru.rustore.sdk.pushclient.RuStorePushClient
import ru.rustore.sdk.pushclient.common.logger.DefaultLogger

class PushXApplication : DaggerApplication() {

    @Inject
    lateinit var preferenceStore: PreferenceStore

    override fun onCreate() {
        super.onCreate()
        RuStorePushClient.init(
            application = this,
            projectId = "zpw73i3UZw1JgSKi1XNKjaJDAnaAQOhc",
            logger = DefaultLogger()
        )
        PushX.addEventHandler(DemoNewPushMessageHandler(applicationContext, preferenceStore))
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }

}
