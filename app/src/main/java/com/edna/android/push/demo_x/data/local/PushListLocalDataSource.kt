package com.edna.android.push.demo_x.data.local

import android.content.res.Resources
import androidx.lifecycle.map
import com.edna.android.push.demo_x.data.PushDataSource
import com.edna.android.push.demo_x.data.Result
import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.data.local.db.PushEntity.Companion.toEntity
import com.edna.android.push.demo_x.data.local.db.PushEntity.Companion.toPush
import com.edna.android.push.demo_x.data.local.db.PushListDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PushListLocalDataSource(
    private val pushListDao: PushListDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PushDataSource {

    override suspend fun getPushList(): Result<List<Push>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(pushListDao.getPushList().map { it.toPush() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getPushListLiveData() = try {
        Result.Success(pushListDao.getPushListLiveData().map { it.map { it.toPush() } })
    } catch (e: Exception) {
        Result.Error(e)
    }


    override suspend fun getPush(pushId: String): Result<Push> = withContext(ioDispatcher) {
        return@withContext try {
            pushListDao.getPushById(pushId)?.let {
                Result.Success(it.toPush())
            }
                ?: Result.Error(Resources.NotFoundException("Push Not fount!"))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getPushLiveData(pushId: String) = pushListDao.getPushLiveData(pushId).map { pushEntity ->
        return@map pushEntity.let {
            try {
                Result.Success(it.toPush())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun savePush(push: Push) = withContext(ioDispatcher) {
        pushListDao.insertPush(push.toEntity())
    }

    override suspend fun clearPushList() {
        pushListDao.clearPushList()
    }

}