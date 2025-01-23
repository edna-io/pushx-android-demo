package com.edna.android.push.demo_x.di

import androidx.lifecycle.ViewModel
import com.edna.android.push.demo_x.app.ViewModelBuilder
import com.edna.android.push.demo_x.app.ViewModelKey
import com.edna.android.push.demo_x.changeappid.ChangeAppIdDialog
import com.edna.android.push.demo_x.changeappid.ChangeAppIdViewModel
import com.edna.android.push.demo_x.clearhistory.ClearHistoryDialog
import com.edna.android.push.demo_x.clearhistory.ClearHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PushDialogModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun pushListFragment(): ClearHistoryDialog
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun pushListFragment1(): ChangeAppIdDialog

    @Binds
    @IntoMap
    @ViewModelKey(ClearHistoryViewModel::class)
    abstract fun bindViewModel(viewModel: ClearHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeAppIdViewModel::class)
    abstract fun bindViewModel1(viewModel: ChangeAppIdViewModel): ViewModel
}