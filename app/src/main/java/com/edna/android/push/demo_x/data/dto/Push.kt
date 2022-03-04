package com.edna.android.push.demo_x.data.dto

data class Push(
    val title: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val date: String = "",
    val bigContentTitle: String = "",
    val bigContentText: String = "",
    val bigImage: String = "",
    val action1: String = "",
    val action2: String = "",
    val messageId: String = "",
    val lights: Int = -1,
    val soundFileName: String? = null,
    val vibration: List<Long> = listOf(),
    val setWhen: Long = System.currentTimeMillis(),
    val deepLink: String? = null,
    val actions: List<ButtonAction> = emptyList(),
    val thumbnailIconColor: Int = -1,
    val customParams: Map<String, String?> = mutableMapOf()
)

