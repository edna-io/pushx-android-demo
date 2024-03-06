package com.edna.android.push.demo_x.changeappid

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.DialogFragment
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.databinding.ChangeAppIdErrorDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ChangeAppIdErrorDialog : DialogFragment() {

    private lateinit var binding: ChangeAppIdErrorDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it, R.style.Dialog2)
            val inflater = requireActivity().layoutInflater
            binding = ChangeAppIdErrorDialogBinding.inflate(inflater)
            val view = binding.root

            builder.setView(view)
                .setPositiveButton(R.string.appid_change_impossible_proceed) { _, _ -> dismiss() }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

}