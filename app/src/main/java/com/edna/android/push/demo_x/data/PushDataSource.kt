package com.edna.android.push.demo_x.data

import androidx.lifecycle.LiveData
import com.edna.android.push.demo_x.data.dto.Push


/**
 * Main entry point for accessing push list data.
 */
interface PushDataSource {

    suspend fun getPushList(): Result<List<Push>>

    fun getPushListLiveData(): Result<LiveData<List<Push>>>

    suspend fun getPush(pushId: String): Result<Push>

    fun getPushLiveData(pushId: String): LiveData<Result<Push>>

    suspend fun savePush(push: Push)

    suspend fun clearPushList()

}