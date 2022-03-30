package com.edna.android.push.demo_x.di

import androidx.lifecycle.ViewModel
import com.edna.android.push.demo_x.activity.MainActivity
import com.edna.android.push.demo_x.activity.MainActivityViewModel
import com.edna.android.push.demo_x.app.ViewModelBuilder
import com.edna.android.push.demo_x.app.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class UserSettingsModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindViewModel(viewModel: MainActivityViewModel): ViewModel

}