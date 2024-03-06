package com.edna.android.push.demo_x.resetaddress

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.edna.android.push.demo_x.R
import com.edna.android.push_lite.huawei.HmsHelper.updateTokenFromHcm
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.huawei.hms.common.ApiException
import ru.rustore.sdk.core.tasks.OnCompleteListener
import ru.rustore.sdk.pushclient.RuStorePushClient.deleteToken
import ru.rustore.sdk.pushclient.RuStorePushClient.getToken

class ResetPushAddressDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.Dialog2)
        val inflater = this.layoutInflater
        val view: View = inflater.inflate(R.layout.reset_push_address_dialog, null)
        dialog.setView(view)
        val checkBoxFcm = view.findViewById<CheckBox>(R.id.fcmToken)
        val checkBoxHcm = view.findViewById<CheckBox>(R.id.hcmToken)
        val checkBoxRcm = view.findViewById<CheckBox>(R.id.rcmToken)

        dialog.setPositiveButton(R.string.delete_btn) { dialog1, which ->
            if (checkBoxFcm.isChecked) {
                resetFcm()
            }
            if (checkBoxHcm.isChecked) {
                resetHcm()
            }
            if (checkBoxRcm.isChecked) {
                resetRcm()
            }
            onDismiss(dialog1)
            dismiss()
        }
        dialog.setNegativeButton(R.string.cancel_btn) { dialog1, which ->
            onDismiss(dialog1)
            dismiss()
        }
        val createdDialog = dialog.create()
        createdDialog.show()
        val btnPos = createdDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        btnPos.isEnabled = false
        checkBoxFcm.setOnClickListener { btnPos.isEnabled = checkBoxFcm.isChecked || checkBoxHcm.isChecked || checkBoxRcm.isChecked }
        checkBoxHcm.setOnClickListener { btnPos.isEnabled = checkBoxFcm.isChecked || checkBoxHcm.isChecked || checkBoxRcm.isChecked }
        checkBoxRcm.setOnClickListener { btnPos.isEnabled = checkBoxFcm.isChecked || checkBoxHcm.isChecked || checkBoxRcm.isChecked }
        return createdDialog
    }

    private fun resetFcm() {
        FirebaseMessaging.getInstance().deleteToken()
            .addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    FirebaseMessaging.getInstance().token
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(
                                    "Installations",
                                    "Installation ID: " + task.result
                                )
                            } else {
                                Log.e(
                                    "Installations",
                                    "Unable to get Installation ID"
                                )
                            }
                        }
                    Log.d("Installations", "Installation deleted")
                } else {

                    Log.e("Installations", "Unable to delete Installation", task.exception)
                }
            }
    }

    private fun resetHcm() {
        val context = requireContext()
        object : Thread() {
            override fun run() {
                try {
                    updateTokenFromHcm(context)
                } catch (ex: ApiException) {
                    Log.e("HmsHelper", "delete token failed$ex")
                }
            }
        }.start()
    }

    private fun resetRcm() {
        deleteToken().addOnCompleteListener(object : OnCompleteListener<Unit> {
            override fun onFailure(throwable: Throwable) {
                Log.e("RuStorePushClient", "Unable to delete rcm token", throwable)
            }

            override fun onSuccess(result: Unit) {
                getToken().addOnCompleteListener(object : OnCompleteListener<String> {
                    override fun onFailure(throwable: Throwable) {
                        Log.e("RuStorePushClient", "Unable to get rcm token", throwable)
                    }

                    override fun onSuccess(result: String) {
                        Log.d("RuStorePushClient", "new token: $result")
                    }
                })
            }
        })
    }

    companion object {
        var TAG = "ResetPushAddressDialog"
    }
}