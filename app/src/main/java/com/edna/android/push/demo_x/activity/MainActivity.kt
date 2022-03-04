package com.edna.android.push.demo_x.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.edna.android.push.demo_x.NavGraphDirections
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.local.sharedpreferences.PreferenceStore
import com.edna.android.push.demo_x.databinding.ActivityBinding
import com.edna.android.push.demo_x.pushprocessing.DemoNewPushMessageHandler
import com.edna.android.push_lite.notification.entity.PushAction
import com.edna.android.push_x.PushX
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    companion object {
        const val PUSH_ACTION_KEY = "action"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preferenceStore: PreferenceStore

    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactory }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PushX.addEventHandler(DemoNewPushMessageHandler(applicationContext, preferenceStore))

        binding = ActivityBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel

        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)
        setupDrawer()
        setupNavigation()
        setupButtons()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment)
        .navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.extras?.getParcelable<PushAction>(PUSH_ACTION_KEY)?.let {
            openPushDetails(it.messageId, it.title, it.action)
        }
    }

    private fun setupDrawer() {
        drawerLayout = binding.drawerLayout
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }

        appBarConfiguration =
            AppBarConfiguration.Builder(R.id.pushListFragment, R.id.pushDetailFragment)
                .setOpenableLayout(drawerLayout)
                .build()
    }

    private fun setupNavigation() {
        val navController: NavController = findNavController(R.id.nav_host_fragment)
        binding.navView
            .setupWithNavController(navController)
    }

    private fun setupButtons() {
        binding.loginButton.setOnClickListener {
            if (viewModel.isUserLogin.value == true) {
                PushX.logout()
                preferenceStore.isUserLogin = false
                preferenceStore.userLogin = null
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.to_loginDialogFragment)
            }

        }

        binding.clearPushHistoryButton.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.to_clearHistoryDialogFragment)
        }
    }

    fun openDrawer() = drawerLayout.open()

    private fun openPushDetails(pushId: String, buttonTitle: String?, buttonAction: String?) {
        val action = NavGraphDirections.toPushDetailFragment(pushId, buttonAction, buttonTitle)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

}

