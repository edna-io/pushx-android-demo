package com.edna.android.push.demo_x.pushlist


import androidx.lifecycle.*
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.PushRepository
import com.edna.android.push.demo_x.data.Result
import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.util.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class PushListViewModel @Inject constructor(
    private val pushRepository: PushRepository,
    preferenceStore: PreferenceStore
) : ViewModel() {

    var items: LiveData<List<Push>> = MutableLiveData<List<Push>>().apply { value = emptyList() }
        set(value) {
            isItemListEmpty = value.map { it.isEmpty() }
            field = value
        }
    var isItemListEmpty = items.map { it.isEmpty() }
    val isUserLogin: LiveData<Boolean> = preferenceStore.isUserLoginLiveData

    private val _openPushEvent = MutableLiveData<Event<String>>()
    val openPushEvent: LiveData<Event<String>> = _openPushEvent

    val userIcon = isUserLogin.map {
        if (it) {
            R.drawable.ic_login_user
        } else {
            R.drawable.ic_anonim
        }
    }

    init {
        loadPushList()
    }

    fun openPush(pushId: String) {
        _openPushEvent.value = Event(pushId)
    }

    fun loadPushList() {
        viewModelScope.launch {
            val pushListResult = pushRepository.getPushListLiveData()
            if (pushListResult is Result.Success) {
                items = pushListResult.data
            } else {
                items = MutableLiveData<List<Push>>().apply { value = emptyList() }
            }
        }
    }
}