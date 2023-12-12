package com.edna.android.push.demo_x

import android.util.Log
import com.edna.android.push_lite.huawei.HcmPushService

class CustomHcmPushService : HcmPushService() {
    companion object{
        const val TAG = "CustomHcm"
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d(TAG, "onNewToken: $newToken")
    }
}