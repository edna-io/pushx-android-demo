package com.edna.android.push.demo_x.login

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.databinding.LoginDialogBinding
import com.edna.android.push.demo_x.util.PhoneUtils
import com.edna.android.push.demo_x.util.PhoneUtils.Companion.COUNTRY_CODE
import com.edna.android.push_x.PushX
import com.edna.android.push_x.auth.SubscriberIdType
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject
import ru.tinkoff.decoro.watchers.FormatWatcher


class LoginDialog : DaggerDialogFragment() {

    @Inject
    lateinit var preferenceStore: PreferenceStore

    lateinit var binding: LoginDialogBinding

    val textWatcherList: MutableList<TextWatcher> = mutableListOf()

    val formatWatcher: FormatWatcher = PhoneUtils.getRuPhoneFormatWatcher()
    lateinit var currentDialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it, R.style.Dialog2)
            val inflater = requireActivity().layoutInflater
            binding = LoginDialogBinding.inflate(inflater)
            val view = binding.root
            setUpSpinner()

            builder.setView(view)
                .setPositiveButton(R.string.login_btn) { _, _ ->
                    val formattedText = binding.identifierValue.text.toString()
                        .replace(" ", "")
                        .replace("-", "")

                    val type = binding.spinner.selectedItem as IdentifierType
                    PushX.login(formattedText, type.value)
                    preferenceStore.userLogin = formattedText
                    preferenceStore.userLoginType = type.value.name
                    preferenceStore.isUserLogin = true
                }
                .setNegativeButton(R.string.cancel_btn) { _, _ -> dialog?.cancel() }
            val dialog = builder.create()
            currentDialog = dialog
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setUpSpinner() {
        val spinner: Spinner = binding.spinner
        IdentifierTypeAdapter(
            requireContext()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item = binding.spinner.selectedItem
                val bt = currentDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                if (item is IdentifierType) {
                    when (item) {
                        IdentifierType.PHONE_NUMBER -> {
                            for (it in textWatcherList) {
                                binding.identifierValue.removeTextChangedListener(it)
                            }

                            textWatcherList.add(formatWatcher)
                            formatWatcher.installOn(binding.identifierValue)

                            binding.identifierValue.inputType = InputType.TYPE_CLASS_PHONE
                            binding.identifierValue.text = SpannableStringBuilder(COUNTRY_CODE)
                            bt.isEnabled = false
                            bt.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayContent))
                            textWatcherList.add(
                                binding.identifierValue.addTextChangedListener {
                                    bt.setEnabledBy(it?.length ?: 0 > 15)
                                }
                            )
                        }
                        else -> {
                            for (it in textWatcherList) {
                                binding.identifierValue.removeTextChangedListener(it)
                            }
                            textWatcherList.add(
                                binding.identifierValue.addTextChangedListener {
                                    bt.setEnabledBy(!it.isNullOrEmpty())
                                }
                            )
                            binding.identifierValue.text = SpannableStringBuilder("")
                            binding.identifierValue.inputType = InputType.TYPE_CLASS_TEXT
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //not support this case
            }
        }
    }

    private fun Button.setEnabledBy(condition: Boolean) {
        if (condition) {
            this.isEnabled = true
            this.setTextColor(ContextCompat.getColor(requireContext(), R.color.positive500))
        } else {
            this.isEnabled = false
            this.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayContent))
        }
    }

    enum class IdentifierType(
        val value: SubscriberIdType,
        val resource: Int
    ) {
        PHONE_NUMBER(SubscriberIdType.PHONE_NUMBER, R.string.login_type_phone),
        EMAIL(SubscriberIdType.EMAIL, R.string.login_type_email),
        FACEBOOK_ID(SubscriberIdType.FACEBOOK, R.string.login_type_facebook),
        TELEGRAM_ID(SubscriberIdType.TELEGRAM, R.string.login_type_telegram),
        UTM(SubscriberIdType.UTM, R.string.login_type_utm),
        COOKIE_ID(SubscriberIdType.COOKIE, R.string.login_type_cookie),
        GOOGLE_ID(SubscriberIdType.GOOGLE, R.string.login_type_google),
        APPLE_ID(SubscriberIdType.APPLE, R.string.login_type_apple),
        YANDEX_ID(SubscriberIdType.YANDEX, R.string.login_type_yandex),
        EXT_USER_ID(SubscriberIdType.EXT_USER, R.string.login_type_userid);
    }

}
