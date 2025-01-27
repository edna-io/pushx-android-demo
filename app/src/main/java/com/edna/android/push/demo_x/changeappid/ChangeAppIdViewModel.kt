package com.edna.android.push.demo_x.changeappid

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edna.android.push.demo_x.data.PushRepository
import com.jakewharton.processphoenix.ProcessPhoenix
import javax.inject.Inject
import kotlinx.coroutines.launch

class ChangeAppIdViewModel @Inject constructor(
    private val pushRepository: PushRepository
) : ViewModel() {

    val ednaId: MutableLiveData<String> =
        MutableLiveData(pushRepository.getEdnaAppId())

    fun saveEdnaId(context: Context) {
        viewModelScope.launch {
            pushRepository.saveEdnaAppId(ednaId.value.toString())
            ProcessPhoenix.triggerRebirth(context)
        }
    }
}