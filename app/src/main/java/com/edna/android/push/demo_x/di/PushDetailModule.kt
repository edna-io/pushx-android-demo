package com.edna.android.push.demo_x.di

import androidx.lifecycle.ViewModel
import com.edna.android.push.demo_x.app.ViewModelBuilder
import com.edna.android.push.demo_x.app.ViewModelKey
import com.edna.android.push.demo_x.pushdetail.PushDetailFragment
import com.edna.android.push.demo_x.pushdetail.PushDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PushDetailModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun pushDetailFragment(): PushDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(PushDetailViewModel::class)
    abstract fun bindViewModel(viewModel: PushDetailViewModel): ViewModel
}