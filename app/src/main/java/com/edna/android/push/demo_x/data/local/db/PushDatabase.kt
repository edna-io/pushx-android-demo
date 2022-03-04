package com.edna.android.push.demo_x.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [PushEntity::class], version = 5, exportSchema = false)
abstract class PushDatabase : RoomDatabase() {

    abstract fun pushListDao(): PushListDao
}