package com.edna.android.push.demo_x.pushprocessing

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.dto.ButtonAction
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.util.putMap
import com.edna.android.push.demo_x.util.putParcelableList
import com.edna.android.push_lite.notification.entity.PushAction
import com.edna.android.push_lite.notification.entity.PushNotification
import com.edna.android.push_x.notification.event.PushXEventHandler


class DemoNewPushMessageHandler(
    private var context: Context,
    private val preferenceStore: PreferenceStore
) : PushXEventHandler(context) {

    override fun onDeviceAddressChanged(
        newDeviceAddress: String,
        deviceUid: String
    ) {
        Toast.makeText(context, "Device address $newDeviceAddress", Toast.LENGTH_LONG)
            .show()
        preferenceStore.deviceAddress = newDeviceAddress
        preferenceStore.deviceUid = deviceUid
    }

    override fun onNewPushMessage(message: PushNotification) {

        val uploadWorkRequest =
            OneTimeWorkRequestBuilder<DatabaseWorker>()
                .setInputData(marshalPushToData(message))
                .build()

        WorkManager
            .getInstance(context)
            .enqueueUniqueWork(DatabaseWorker.TAG, ExistingWorkPolicy.KEEP, uploadWorkRequest)

        super.onNewPushMessage(
            message.copy(
                thumbnailIconUrl = message.thumbnailIconUrl ?: "ic_demo_icon",
                thumbnailIconColor = ContextCompat.getColor(context, R.color.iconColor)
            )
        )
    }

    private fun marshalPushToData(message: PushNotification): Data {
        val extras = Data.Builder()
        extras.putString(PushNotification::title.name, message.title)
        extras.putString(PushNotification::message.name, message.message)
        extras.putString(PushNotification::logoUrl.name, message.logoUrl)
        extras.putString(PushNotification::chlSentAt.name, message.chlSentAt)
        extras.putString(PushNotification::bigTitle.name, message.bigTitle)
        extras.putString(PushNotification::bigMessage.name, message.bigMessage)
        extras.putString(PushNotification::bigPictureUrl.name, message.bigPictureUrl)
        extras.putString(PushNotification::messageId.name, message.messageId)
        extras.putInt(PushNotification::lights.name, message.lights)
        extras.putString(PushNotification::soundFileName.name, message.soundFileName)
        extras.putLongArray(PushNotification::vibration.name, message.vibration.toLongArray())
        extras.putString(PushNotification::chlSentAt.name, message.chlSentAt)
        extras.putString(PushNotification::deepLink.name, message.deepLink)
        extras.putParcelableList(PushNotification::actions.name, message.actions.toButtonActionList())
        extras.putMap(PushNotification::customParams.name, message.customParams)
        extras.putInt(PushNotification::thumbnailIconColor.name, message.thumbnailIconColor)
        return extras.build()
    }

    private fun List<PushAction.ButtonAction>.toButtonActionList(): ArrayList<ButtonAction> = this
        .flatMap {

            return@flatMap arrayListOf(
                ButtonAction(
                    title = it.buttonTitle ?: "",
                    action = it.buttonAction ?: "",
                    messageId = it.messageId
                )
            )
        } as ArrayList<ButtonAction>
}
