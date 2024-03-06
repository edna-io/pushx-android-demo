package com.edna.android.push.demo_x.pushprocessing

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.edna.android.push.demo_x.data.DefaultPushRepository
import com.edna.android.push.demo_x.data.PushRepository
import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.data.local.PushListLocalDataSource
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.di.SingletonDB
import com.edna.android.push.demo_x.util.getMap
import com.edna.android.push.demo_x.util.getParcelableList
import com.edna.android.push_lite.notification.entity.PushNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val pushRepository = pushRepo(appContext)

    private fun pushRepo(context: Context): PushRepository {
        val pushListLocalDataSource =
            PushListLocalDataSource(SingletonDB.getInstance(context).pushListDao())
        return DefaultPushRepository(pushListLocalDataSource, Dispatchers.IO, PreferenceStore(context))
    }

    companion object {
        const val TAG = "DatabaseWorker"
    }

    override suspend fun doWork(): Result {
        val extras = inputData
        savePush(
            Push(
                title = extras.getString(PushNotification::title.name) ?: "",
                description = extras.getString(PushNotification::message.name) ?: "",
                iconUrl = extras.getString(PushNotification::logoUrl.name) ?: "",
                setWhen = extras.getLong(
                    PushNotification::chlSentAt.name,
                    System.currentTimeMillis()
                ),
                bigContentTitle = extras.getString(PushNotification::bigTitle.name) ?: "",
                bigContentText = extras.getString(PushNotification::bigMessage.name) ?: "",
                bigImage = extras.getString(PushNotification::bigPictureUrl.name) ?: "",
                messageId = extras.getString(PushNotification::messageId.name) ?: "",
                lights = extras.getInt(PushNotification::lights.name, -1),
                soundFileName = extras.getString(PushNotification::soundFileName.name) ?: "",
                vibration = extras.getLongArray(PushNotification::vibration.name)?.toList()
                    ?: emptyList(),
                deepLink = extras.getString(PushNotification::deepLink.name) ?: "",
                thumbnailIconColor = extras.getInt(PushNotification::thumbnailIconColor.name, -1),
                actions = extras.getParcelableList(PushNotification::actions.name),
                customParams = extras.getMap(PushNotification::customParams.name) ?: emptyMap()
            )
        )

        return Result.success()
    }

    private suspend fun savePush(push: Push) = withContext(Dispatchers.IO) {
        pushRepository.savePush(push)
    }

}