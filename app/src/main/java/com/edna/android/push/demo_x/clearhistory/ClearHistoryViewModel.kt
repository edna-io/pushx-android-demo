package com.edna.android.push.demo_x.clearhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edna.android.push.demo_x.data.PushRepository
import com.edna.android.push.demo_x.data.local.db.PushEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClearHistoryViewModel @Inject constructor(
    private val pushRepository: PushRepository
) : ViewModel() {

    private val _push = MutableLiveData<PushEntity>()
    val push: LiveData<PushEntity> = _push

    fun clearHistory() {
        viewModelScope.launch {
            pushRepository.clearPushList()
        }
    }
}