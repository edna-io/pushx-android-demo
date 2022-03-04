package com.edna.android.push.demo_x.di

import androidx.lifecycle.ViewModel
import com.edna.android.push.demo_x.app.ViewModelBuilder
import com.edna.android.push.demo_x.app.ViewModelKey
import com.edna.android.push.demo_x.clearhistory.ClearHistoryDialog
import com.edna.android.push.demo_x.clearhistory.ClearHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PushDialogModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun pushListFragment(): ClearHistoryDialog

    @Binds
    @IntoMap
    @ViewModelKey(ClearHistoryViewModel::class)
    abstract fun bindViewModel(viewModel: ClearHistoryViewModel): ViewModel
}