package com.toloza.avengersapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.toloza.avengersapp.R
import com.toloza.avengersapp.databinding.ActivityMainBinding
import com.toloza.avengersapp.extensions.setToolbar
import com.toloza.avengersapp.extensions.visible
import com.toloza.avengersapp.ui.view.HomeNavigationListener
import com.toloza.avengersapp.ui.view.model.HomeNavigationTab
import com.toloza.avengersapp.ui.viewmodel.LoginCommunicationUiModel
import com.toloza.avengersapp.ui.viewmodel.LoginCommunicationViewModel
import com.toloza.avengersapp.ui.viewmodel.MainUiModel
import com.toloza.avengersapp.ui.viewmodel.MainViewModel
import com.toloza.avengersapp.util.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), HomeNavigationListener {
    private val communicationViewModel by viewModel<LoginCommunicationViewModel>()
    private val viewModel: MainViewModel by inject()

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController

    private val uiStateObserver = Observer<MainUiModel>{
        it.showCharactersScreen?.consume()?.let { showCharactersScreen() }
        it.showEventsScreen?.consume()?.let { showEventsScreen() }
    }

    private val communicationObserver = Observer<LoginCommunicationUiModel> {
        it.notifyLoginSuccess?.consume()?.let { tabList -> showBottomNavigation(tabList) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setToolbar(
            binding.toolbarLayout.toolbar,
            binding.toolbarLayout.title,
            getString(R.string.toolbar_title)
        )

        setUpNavigationProperties()

        communicationViewModel.uiState.observe(this, communicationObserver)
        viewModel.uiState.observe(this, uiStateObserver)
    }

    override fun onTabItemClicked(itemSelected: HomeNavigationTab) {
        viewModel.onTabItemClicked(itemSelected)
    }

    private fun showBottomNavigation(tabList: List<HomeNavigationTab>) {
        binding.homeNavigation.visible()
        binding.homeNavigation.setItemList(tabList, this)
        binding.navHostFragment.findNavController().navigate(R.id.characterFragment)
    }

    private fun setUpNavigationProperties() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun showCharactersScreen() {
        navController.navigate(R.id.characterFragment)
    }

    private fun showEventsScreen() {
        navController.navigate(R.id.eventsFragment)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {

            }
        }
    }
}