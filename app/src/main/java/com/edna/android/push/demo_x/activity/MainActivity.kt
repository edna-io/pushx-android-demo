package com.edna.android.push.demo_x.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
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
import com.edna.android.push_lite.notification.entity.PushAction
import com.edna.android.push_x.PushX
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    companion object {
        const val PUSH_ACTION_KEY = "action"
        const val ACTION_PARAMS = "action_params"
        const val REQUEST_CODE = 45978
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

        binding = ActivityBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel

        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)
        setupDrawer()
        setupNavigation()
        setupButtons()

        openPushDetailsByIntent(intent)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                Toast(this).also {
                    it.setText("Permission granted")
                }.show()
                } else {
                    Toast(this).also {
                        it.setText("Permission not granted")
                    }.show()
                }
            }
            else -> {
                // Ignore all other requests.
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment)
        .navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        openPushDetailsByIntent(intent)
    }

    private fun openPushDetailsByIntent(intent: Intent?) {
        intent?.extras?.parcelable<PushAction>(PUSH_ACTION_KEY)?.let {
            val extraParams = intent.extras?.getBundle(ACTION_PARAMS)
            openPushDetails(it.messageId, it.title, it.action, extraParams)
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

    private fun openPushDetails(
        pushId: String,
        buttonTitle: String?,
        buttonAction: String?,
        bundle: Bundle? = null
    ) {
        val action =
            NavGraphDirections.toPushDetailFragment(pushId, buttonAction, buttonTitle, bundle)
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

}

