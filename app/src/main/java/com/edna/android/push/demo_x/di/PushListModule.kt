package com.edna.android.push.demo_x.di

import androidx.lifecycle.ViewModel
import com.edna.android.push.demo_x.app.ViewModelBuilder
import com.edna.android.push.demo_x.app.ViewModelKey
import com.edna.android.push.demo_x.pushlist.PushListFragment
import com.edna.android.push.demo_x.pushlist.PushListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class PushListModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun pushListFragment(): PushListFragment

    @Binds
    @IntoMap
    @ViewModelKey(PushListViewModel::class)
    abstract fun bindViewModel(viewModel: PushListViewModel): ViewModel
}
