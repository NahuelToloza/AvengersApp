package com.toloza.avengersapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.*
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.toloza.avengersapp.NavGraphDirections
import com.toloza.avengersapp.R
import com.toloza.avengersapp.databinding.ActivityMainBinding
import com.toloza.avengersapp.extensions.setToolbar
import com.toloza.avengersapp.extensions.visible
import com.toloza.avengersapp.ui.viewmodel.LoginCommunicationUiModel
import com.toloza.avengersapp.ui.viewmodel.LoginCommunicationViewModel
import com.toloza.avengersapp.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val communicationViewModel: LoginCommunicationViewModel by viewModel()

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController

    private val uiModelObserver = Observer<LoginCommunicationUiModel> {
        it.notifyLoginSuccess?.consume()?.let { showBottomNavigation() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setToolbar(
            binding.toolbarLayout.toolbar,
            binding.toolbarLayout.title,
            getString(R.string.toolbar_title)
        )

        navController = binding.navHostFragment.findNavController()
        binding.bottomNavigation.setupWithNavController(navController)

        communicationViewModel.uiModel.observe(this, uiModelObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.characters -> {
                val action = NavGraphDirections.actionGlobalEventsFragment()
                navController.navigate(action)
                return true
            }
            else -> {
                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showBottomNavigation() {
        binding.bottomNavigation.visible()
        binding.navHostFragment.findNavController().navigate(R.id.characterFragment)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {

            }
        }
    }
}