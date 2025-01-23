package com.edna.android.push.demo_x.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.di.ResourceProvider
import com.edna.android.push.demo_x.util.PhoneUtils.Companion.PHONE_SLOTS
import com.edna.android.push.demo_x.util.combine
import com.edna.android.push_lite.huawei.HmsHelper
import com.edna.android.push_x.PushX
import com.edna.android.push_x.auth.SubscriberIdType
import javax.inject.Inject
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser

class MainActivityViewModel
@Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val preferenceStore: PreferenceStore
) : ViewModel() {

    val isUserLogin: LiveData<Boolean> = preferenceStore.isUserLoginLiveData
    val userLogin: LiveData<String> =
        combine(preferenceStore.userLoginLiveData, preferenceStore.userLoginTypeLiveData)
        { login, loginType ->
            if (login.isNullOrEmpty()) {
                return@combine resourceProvider.getString(R.string.anonymous_header)
            }
            if (SubscriberIdType.getByName(loginType ?: "") == SubscriberIdType.PHONE_NUMBER) {
                val mask =
                    MaskImpl.createTerminated(UnderscoreDigitSlotsParser().parseSlots(PHONE_SLOTS))
                mask.insertFront(login)
                return@combine mask.toString()
            }
            return@combine login
        }

    val loginOrLogoutText = isUserLogin.map {
        if (it) {
            R.string.logout_btn
        } else {
            R.string.login_btn
        }
    }

    val loginOrLogoutIcon = isUserLogin.map {
        if (it) {
            R.drawable.ic_exit
        } else {
            R.drawable.ic_profile
        }
    }

    val name = Build.USER.replace("\"", "")
    val deviceAddress = preferenceStore.deviceAddressLiveData
    val deviceId = preferenceStore.deviceUidLiveData

    fun copyData(): String {
        val nameText = resourceProvider.getString(R.string.lmenu_device_name)
        val deviceAddressText = resourceProvider.getString(R.string.lmenu_device_address)
        val deviceIdText = resourceProvider.getString(R.string.lmenu_device_id)
        return "$nameText: $name,\n$deviceAddressText: ${deviceAddress.value},\n$deviceIdText: ${deviceId.value}"
    }

    fun copyPushData(context: Context) {
        val text = PushX.getDetDeviceInfo(context).toString()
        val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
        val clip = ClipData.newPlainText("label", text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(context, R.string.toast_copy_ok, Toast.LENGTH_LONG)
            .show()
    }

    fun copyName() = "${resourceProvider.getString(R.string.lmenu_device_name)}: $name"

    fun copyAddress() =
        "${resourceProvider.getString(R.string.lmenu_device_address)}: ${deviceAddress.value}"

    fun copyDeviceId() =
        "${resourceProvider.getString(R.string.lmenu_device_id)}: ${deviceId.value}"
}


