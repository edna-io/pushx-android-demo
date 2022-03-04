package com.edna.android.push.demo_x.clearhistory

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.databinding.CleanHistoryDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class ClearHistoryDialog : DaggerDialogFragment() {

    private lateinit var binding: CleanHistoryDialogBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ClearHistoryViewModel> { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it, R.style.Dialog2)
            val inflater = requireActivity().layoutInflater
            binding = CleanHistoryDialogBinding.inflate(inflater)
            val view = binding.root

            builder.setView(view)
                .setPositiveButton(R.string.delete_btn) { _, _ -> viewModel.clearHistory() }
                .setNegativeButton(R.string.cancel_btn) { _, _ -> dialog?.cancel() }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

}