package com.edna.android.push.demo_x.data

import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.di.ApplicationModule
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultPushRepository @Inject constructor(
    @ApplicationModule.PushListLocalDataSource private val pushListLocalDataSource: PushDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val sharedPref: PreferenceStore
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

    override fun saveEdnaAppId(ednaAppId: String) {
        sharedPref.ednaId = ednaAppId
    }

    override fun getEdnaAppId(): String {
        return sharedPref.ednaId ?: ""
    }

}