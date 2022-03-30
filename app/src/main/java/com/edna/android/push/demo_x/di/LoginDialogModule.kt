package com.edna.android.push.demo_x.di

import com.edna.android.push.demo_x.login.LoginDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class LoginDialogModule {

    @ContributesAndroidInjector
    internal abstract fun loginFragment(): LoginDialog

}