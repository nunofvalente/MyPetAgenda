package com.nunovalente.android.mypetagenda

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nunovalente.android.mypetagenda.databinding.ActivityMainBinding
import com.nunovalente.android.mypetagenda.ui.common.activity.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_activity -> showBottomNav()
                R.id.navigation_home -> showBottomNav()
                R.id.navigation_gallery -> showBottomNav()
                R.id.navigation_mypets -> showBottomNav()
                R.id.navigation_camera -> hideStatusBar()
                else -> hideBottomNav()
            }
        }
    }


    @Suppress("DEPRECATION")
    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        binding.navView.visibility = View.GONE
        binding.appBarMain.visibility = View.GONE
    }

    @Suppress("DEPRECATION")
    private fun showBottomNav() {
        binding.toolbarTitle.text = getString(R.string.app_name)
        binding.navView.visibility = View.VISIBLE
        binding.appBarMain.visibility = View.VISIBLE

        val decorView: View = window.decorView // Hide the status bar.
        val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = uiOptions
    }

    @Suppress("DEPRECATION")
    private fun showStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
        binding.appBarMain.visibility = View.GONE
    }
}