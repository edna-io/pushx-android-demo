package com.edna.android.push.demo_x.pushprocessing

import android.content.Context
import androidx.work.*
import com.edna.android.push.demo_x.data.PushRepository
import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.util.getMap
import com.edna.android.push.demo_x.util.getParcelableList
import com.edna.android.push_lite.notification.entity.PushNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class DatabaseWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private val pushRepository: Provider<PushRepository>
) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "DatabaseWorker"
    }

    override suspend fun doWork(): Result {
        val extras = inputData
        savePush(
            Push(
                title = extras.getString(PushNotification::title.name) ?: "",
                description = extras.getString(PushNotification::message.name) ?: "",
                iconUrl = extras.getString(PushNotification::smallIconUrl.name) ?: "",
                setWhen = extras.getLong(PushNotification::chlSentAt.name, System.currentTimeMillis()),
                bigContentTitle = extras.getString(PushNotification::bigTitle.name) ?: "",
                bigContentText = extras.getString(PushNotification::bigMessage.name) ?: "",
                bigImage = extras.getString(PushNotification::largeIconUrl.name) ?: "",
                messageId = extras.getString(PushNotification::messageId.name) ?: "",
                lights = extras.getInt(PushNotification::lights.name, -1),
                soundFileName = extras.getString(PushNotification::soundFileName.name) ?: "",
                vibration = extras.getLongArray(PushNotification::vibration.name)?.toList() ?: emptyList(),
                deepLink = extras.getString(PushNotification::deepLink.name) ?: "",
                thumbnailIconColor = extras.getInt(PushNotification::thumbnailIconColor.name, -1),
                actions = extras.getParcelableList(PushNotification::actions.name),
                customParams = extras.getMap(PushNotification::customParams.name) ?: emptyMap()
            )
        )

        return Result.success()
    }

    private suspend fun savePush(push: Push) = withContext(Dispatchers.IO) {
        pushRepository.get().savePush(push)
    }

}


class MainDelegatingWorkerFactory @Inject constructor(
    pushRepository: Provider<PushRepository>
) : DelegatingWorkerFactory() {
    init {
        addFactory(DataControlWorkerFactory(pushRepository))
    }
}

class DataControlWorkerFactory(private val pushRepository: Provider<PushRepository>) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            DatabaseWorker::class.java.name ->
                DatabaseWorker(appContext, workerParameters, pushRepository)
            else -> null
        }

    }
}
