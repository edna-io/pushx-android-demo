package com.edna.android.push.demo_x.changeappid

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.databinding.ChangeAppIdDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class ChangeAppIdDialog : DaggerDialogFragment() {

    private lateinit var binding: ChangeAppIdDialogBinding
    private lateinit var createdDialog: AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ChangeAppIdViewModel> { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let { it ->
            val builder = MaterialAlertDialogBuilder(it, R.style.Dialog2)
            val inflater = requireActivity().layoutInflater
            binding = ChangeAppIdDialogBinding.inflate(inflater)
            binding.viewModel = viewModel
            val view = binding.root

            builder.setView(view)
                .setPositiveButton(R.string.appid_change_proceed) { _, _ ->
                    viewModel.saveEdnaId(requireContext())
                }
                .setNegativeButton(R.string.appid_change_cancel) { _, _ -> dialog?.cancel() }
            createdDialog = builder.create()
            binding.editTextAppId.addTextChangedListener { editable ->
                createdDialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled =
                    !editable.isNullOrEmpty()
            }

            createdDialog

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()
        createdDialog.getButton(Dialog.BUTTON_POSITIVE).isEnabled =
            !viewModel.ednaId.value.isNullOrEmpty()
        binding.editTextAppId.requestFocus()
    }

}