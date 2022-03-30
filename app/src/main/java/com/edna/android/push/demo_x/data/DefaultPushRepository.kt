package com.edna.android.push.demo_x.data

import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.di.ApplicationModule
import kotlinx.coroutines.*
import javax.inject.Inject

class DefaultPushRepository @Inject constructor(
    @ApplicationModule.PushListLocalDataSource private val pushListLocalDataSource: PushDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PushRepository {

    override suspend fun getPushList(): Result<List<Push>> = withContext(ioDispatcher) {
        pushListLocalDataSource.getPushList()
    }

    override fun getPushListLiveData() = pushListLocalDataSource.getPushListLiveData()

    override suspend fun getPush(pushId: String): Result<Push> {
        return pushListLocalDataSource.getPush(pushId)
    }

    override fun getPushLiveData(pushId: String) = pushListLocalDataSource.getPushLiveData(pushId)

    override suspend fun savePush(push: Push) {
        coroutineScope {
            launch {
                pushListLocalDataSource.savePush(push)
            }
        }
    }

    override suspend fun clearPushList() {
        coroutineScope {
            launch { pushListLocalDataSource.clearPushList() }
        }
    }

}