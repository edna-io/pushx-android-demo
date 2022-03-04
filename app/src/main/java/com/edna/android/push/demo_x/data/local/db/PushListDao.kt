package com.edna.android.push.demo_x.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * Data Access Object for the push table.
 */
@Dao
interface PushListDao {

    @Query("SELECT * FROM PushList")
    suspend fun getPushList(): List<PushEntity>

    @Query("SELECT * FROM PushList")
    fun getPushListLiveData(): LiveData<List<PushEntity>>

    @Query("SELECT * FROM PushList WHERE messageId = :pushId")
    suspend fun getPushById(pushId: String): PushEntity?

    @Query("SELECT * FROM PushList WHERE messageId = :pushId")
    fun getPushLiveData(pushId: String): LiveData<PushEntity>

    /**
     * Insert push in the database. If the push already exists, ignore it.
     *
     * @param pushEntity the push to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPush(pushEntity: PushEntity)

    @Query("DELETE FROM PushList")
    suspend fun clearPushList()

}