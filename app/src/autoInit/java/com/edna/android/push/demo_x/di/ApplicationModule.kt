package com.edna.android.push.demo_x.di

import android.content.Context
import androidx.annotation.StringRes
import androidx.room.Room
import com.edna.android.push.demo_x.data.DefaultPushRepository
import com.edna.android.push.demo_x.data.PushDataSource
import com.edna.android.push.demo_x.data.PushRepository
import com.edna.android.push.demo_x.data.local.PushListLocalDataSource
import com.edna.android.push.demo_x.data.local.db.PushDatabase
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class PushListLocalDataSource

    @JvmStatic
    @Singleton
    @PushListLocalDataSource
    @Provides
    fun providePushListLocalDataSource(
        database: PushDatabase,
        ioDispatcher: CoroutineDispatcher
    ): PushDataSource = PushListLocalDataSource(
        database.pushListDao(), ioDispatcher
    )

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context) = SingletonDB.getInstance(context)

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @JvmStatic
    @Singleton
    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider =
        ResourceProvider(context.applicationContext)

    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): PreferenceStore = PreferenceStore(context)
}

@Module
abstract class ApplicationModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultPushRepository): PushRepository
}

class ResourceProvider(private val context: Context) {
    fun getString(@StringRes resId: Int, vararg args: String?) = context.getString(resId, *args)
}
