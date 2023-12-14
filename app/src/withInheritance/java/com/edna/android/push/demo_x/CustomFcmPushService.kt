package com.edna.android.push.demo_x

import android.util.Log
import com.edna.android.push_lite.fcm.FcmPushService

class CustomFcmPushService : FcmPushService() {
    companion object{
        const val TAG = "CustomFcm"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
    }

}