package com.edna.android.push.demo_x.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonAction(
    var title: String = "",
    val action: String = "",
    val messageId: String = "",
) : Parcelable