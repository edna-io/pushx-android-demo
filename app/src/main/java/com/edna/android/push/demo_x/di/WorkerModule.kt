package com.edna.android.push.demo_x.di

import com.edna.android.push.demo_x.pushprocessing.DatabaseWorker
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WorkerModule {

    @ContributesAndroidInjector
    abstract fun contributeMyService(): DatabaseWorker

}
