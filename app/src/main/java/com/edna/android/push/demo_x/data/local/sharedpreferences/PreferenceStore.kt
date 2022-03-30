package com.edna.android.push.demo_x.data.local.sharedpreferences

import android.content.Context
import com.edna.android.push_x.extentions.boolean
import com.edna.android.push_x.extentions.stringNullable

class PreferenceStore(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        STORE_NAME,
        Context.MODE_PRIVATE
    )

    var deviceAddress by sharedPreferences.stringNullable(key = { PREFERENCE_DEVICE_ADDRESS })
    val deviceAddressLiveData = sharedPreferences.stringLiveData(PREFERENCE_DEVICE_ADDRESS, "")
    var deviceUid by sharedPreferences.stringNullable(key = { PREFERENCE_DEVICE_UID })
    var deviceUidLiveData = sharedPreferences.stringLiveData(PREFERENCE_DEVICE_UID, "")

    @set:JvmName("setIsUserLogin")
    var isUserLogin by sharedPreferences.boolean { PREFERENCE_IS_USER_LOGIN }
    val isUserLoginLiveData = sharedPreferences.booleanLiveData(PREFERENCE_IS_USER_LOGIN, false)
    var userLogin by sharedPreferences.stringNullable { PREFERENCE_USER_LOGIN }
    var userLoginLiveData = sharedPreferences.stringLiveData(PREFERENCE_USER_LOGIN, "")
    var userLoginType by sharedPreferences.stringNullable { PREFERENCE_USER_LOGIN_TYPE }
    var userLoginTypeLiveData = sharedPreferences.stringLiveData(PREFERENCE_USER_LOGIN_TYPE, "")


    companion object {
        private const val STORE_NAME = "com.edna.android.push.demo_x.data.local.PreferenceStore"
        private const val BASE_NAMESPACE = "com.edna.push.demo_x"
        private const val PREFERENCE_DEVICE_UID = BASE_NAMESPACE + "device.id"
        private const val PREFERENCE_DEVICE_ADDRESS = BASE_NAMESPACE + "device.address"
        private const val PREFERENCE_IS_USER_LOGIN = BASE_NAMESPACE + "id_is_user_login"
        private const val PREFERENCE_USER_LOGIN = BASE_NAMESPACE + "id_user_login"
        private const val PREFERENCE_USER_LOGIN_TYPE = BASE_NAMESPACE + "id_user_login_type"
    }
}