package com.edna.android.push.demo_x.pushlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.edna.android.push.demo_x.activity.MainActivity
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.databinding.PushListFragBinding
import com.edna.android.push.demo_x.util.EventObserver
import com.edna.android.push_x.PushX
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PushListFragment : DaggerFragment() {

    companion object{
        const val TAG = "PushListFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preferenceStore: PreferenceStore

    private val viewModel by viewModels<PushListViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: PushListFragBinding

    private lateinit var listAdapter: PushAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = PushListFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        (activity as AppCompatActivity).setSupportActionBar(viewDataBinding.toolbar)
        viewDataBinding.profileButton
            .setOnClickListener {
                (activity as MainActivity).openDrawer()
            }
        setHasOptionsMenu(true)
        viewDataBinding.loginButton.setOnClickListener {
            if (viewModel.isUserLogin.value == true) {
                PushX.logout()
                preferenceStore.isUserLogin = false
            } else {
                findNavController().navigate(R.id.to_loginDialogFragment)
            }

        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupNavigation()
        setupListAdapter()
        viewModel.loadPushList()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = PushAdapter(viewModel)
            viewDataBinding.pushList.adapter = listAdapter
        } else {
            Log.w(TAG, "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupNavigation() {
        viewModel.openPushEvent.observe(this.viewLifecycleOwner, EventObserver {
            openPushDetails(it)
        })
    }

    private fun openPushDetails(pushId: String) {
        val action = PushListFragmentDirections.actionPushDetailFragmentToPushDetailFragment(pushId, null, null)
        findNavController().navigate(action)
    }
}