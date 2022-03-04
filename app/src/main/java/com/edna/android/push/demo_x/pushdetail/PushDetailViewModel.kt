package com.edna.android.push.demo_x.pushdetail

import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.*
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.PushRepository
import com.edna.android.push.demo_x.data.Result
import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.di.ResourceProvider
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject


class PushDetailViewModel @Inject constructor(
    private val pushRepository: PushRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val pushIdFlow = MutableStateFlow("")

    private val pushResult = pushIdFlow.flatMapConcat { pushId ->
        if (pushId.isNotEmpty()) {
            val pushResult = pushRepository.getPushLiveData(pushId)
            return@flatMapConcat pushResult.asFlow()
        }
        return@flatMapConcat flowOf(Result.Error(Exception("Push id not fount!")))
    }.asLiveData()

    val isPushNotFound = pushResult.map { it !is Result.Success }

    val push = pushResult.map {
        if (it is Result.Success) {
            it.data
        } else {
            Push()
        }
    }

    val items = push.map { it.actions }
    private val _navMessage = MutableLiveData<String>()
    val navMessage = com.edna.android.push.demo_x.util.combine(_navMessage, push) { navMessage, push ->
        if (navMessage.isNullOrEmpty()) {
            val link = push?.deepLink ?: ""
            return@combine resourceProvider.getString(R.string.push_details_nav_info_default, link)
        }
        navMessage
    }

    val additional = push.map {
        val spun = buildSpannedString {
            it.customParams.forEach { (key, value) ->
                bold { append(key) }
                append(": ${value},\n")
            }
        }

        return@map spun.dropLast(2)
    }

    fun loadPushById(pushId: String, buttonAction: String?, buttonActionTitle: String?) {
        pushIdFlow.value = pushId
        if (buttonAction != null && buttonActionTitle != null) {
            _navMessage.value =
                resourceProvider.getString(R.string.push_details_nav_info, buttonActionTitle, buttonAction)
        } else {
            _navMessage.value = ""
        }
    }
}