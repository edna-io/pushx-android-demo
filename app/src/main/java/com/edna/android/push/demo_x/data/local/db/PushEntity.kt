package com.edna.android.push.demo_x.data.local.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edna.android.push.demo_x.data.dto.ButtonAction
import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.data.local.db.ButtonActionEntity.Companion.toButtonAction
import com.edna.android.push.demo_x.data.local.db.ButtonActionEntity.Companion.toEntity
import kotlinx.parcelize.Parcelize


@Entity(tableName = "PushList")
data class PushEntity @JvmOverloads constructor(
    @PrimaryKey
    @ColumnInfo(name = "messageId") val messageId: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "iconUrl") val iconUrl: String = "",
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "bigContentTitle") val bigContentTitle: String = "",
    @ColumnInfo(name = "bigContentText") val bigContentText: String = "",
    @ColumnInfo(name = "bigImage") val bigImage: String = "",
    @ColumnInfo(name = "action1") val action1: String = "",
    @ColumnInfo(name = "action2") val action2: String = "",
    @ColumnInfo(name = "lights") val lights: Int = -1,
    @ColumnInfo(name = "soundFileName") val soundFileName: String? = null,
    @ColumnInfo(name = "vibration") val vibration: List<Long> = listOf(),
    @ColumnInfo(name = "setWhen") val setWhen: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "deepLink") val deepLink: String? = null,
    @ColumnInfo(name = "actions") val actionEntities: List<ButtonActionEntity> = emptyList(),
    @ColumnInfo(name = "thumbnailIconColor") val thumbnailIconColor: Int = -1,
    @ColumnInfo(name = "customParams") val customParams: Map<String, String?> = mutableMapOf()
) {
    companion object {
        fun Push.toEntity() = PushEntity(
            messageId = this.messageId,
            title = this.title,
            description = this.description,
            iconUrl = this.iconUrl,
            date = this.date,
            bigContentTitle = this.bigContentTitle,
            bigContentText = this.bigContentText,
            bigImage = this.bigImage,
            action1 = this.action1,
            action2 = this.action2,
            lights = this.lights,
            soundFileName = this.soundFileName,
            vibration = this.vibration,
            setWhen = this.setWhen,
            deepLink = this.deepLink,
            actionEntities = this.actions.map { it.toEntity() },
            thumbnailIconColor = this.thumbnailIconColor,
            customParams = this.customParams
        )

        fun PushEntity.toPush() = Push(
            title = this.title,
            description = this.description,
            iconUrl = this.iconUrl,
            date = this.date,
            bigContentTitle = this.bigContentTitle,
            bigContentText = this.bigContentText,
            bigImage = this.bigImage,
            action1 = this.action1,
            action2 = this.action2,
            messageId = this.messageId,
            lights = this.lights,
            soundFileName = this.soundFileName,
            vibration = this.vibration,
            setWhen = this.setWhen,
            deepLink = this.deepLink,
            actions = this.actionEntities.map { it.toButtonAction() },
            thumbnailIconColor = this.thumbnailIconColor,
            customParams = this.customParams
        )

    }
}

@Parcelize
data class ButtonActionEntity @JvmOverloads constructor(
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "action") val action: String = "",
    @ColumnInfo(name = "messageId") val messageId: String = "",
) : Parcelable {
    companion object {
        fun ButtonAction.toEntity() = ButtonActionEntity(
            this.title,
            this.action,
            this.messageId
        )

        fun ButtonActionEntity.toButtonAction() = ButtonAction(
            this.title,
            this.action,
            this.messageId
        )
    }
}
