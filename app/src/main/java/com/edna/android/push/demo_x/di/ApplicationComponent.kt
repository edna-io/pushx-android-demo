package com.edna.android.push.demo_x.di

import android.content.Context
import com.edna.android.push.demo_x.app.PushXApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        PushListModule::class,
        PushDetailModule::class,
        PushDialogModule::class,
        WorkerModule::class,
        LoginDialogModule::class,
        UserSettingsModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<PushXApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

}