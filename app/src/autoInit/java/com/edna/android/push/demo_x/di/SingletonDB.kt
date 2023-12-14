package com.edna.android.push.demo_x.di

import android.content.Context
import androidx.room.Room
import com.edna.android.push.demo_x.data.local.db.PushDatabase

object SingletonDB {

    private const val DATABASE_NAME = "PushDatabase.db"

    @Volatile
    private var instance: PushDatabase? = null

    fun getInstance(context: Context): PushDatabase {
        return instance ?: synchronized(this) {
            instance ?: createPushRepo(context).also { instance = it }
        }
    }

    private fun createPushRepo(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        PushDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}
