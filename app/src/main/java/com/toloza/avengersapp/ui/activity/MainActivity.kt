package com.toloza.avengersapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.toloza.avengersapp.R
import com.toloza.avengersapp.databinding.ActivityMainBinding
import com.toloza.avengersapp.extensions.gone
import com.toloza.avengersapp.extensions.visible
import com.toloza.avengersapp.ui.view.HomeNavigationListener
import com.toloza.avengersapp.ui.view.model.HomeNavigationTab
import com.toloza.avengersapp.ui.viewmodel.*
import com.toloza.avengersapp.ui.viewmodel.uimodel.main.MainUiModel
import com.toloza.avengersapp.util.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), HomeNavigationListener {
    private val communicationViewModel by viewModel<LoginCommunicationViewModel>()
    private val navigationViewModel by viewModel<NavigationCommunicationViewModel>()
    private val viewModel: MainViewModel by inject()

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController

    private val uiStateObserver = Observer<MainUiModel> {
        it.showCharactersScreen?.consume()?.let { showCharactersScreen() }
        it.showEventsScreen?.consume()?.let { showEventsScreen() }
    }

    private val communicationObserver = Observer<LoginCommunicationUiModel> {
        it.notifyLoginSuccess?.consume()?.let { tabList -> showBottomNavigation(tabList) }
    }

    private val navigationObserver = Observer<NavigationCommunicationUiModel> {
        it.changeTitle?.consume()?.let { title -> changeToolbarTitle(title) }
        it.showNavigationBar?.consume()
            ?.let { shouldShow -> changeVisibilityNavigation(shouldShow) }
    }

    private fun changeVisibilityNavigation(shouldShow: Boolean) {
        if (shouldShow) {
            binding.homeNavigation.visible()
        } else {
            binding.homeNavigation.gone()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setUpNavigationProperties()

        setUpObservers()
    }

    override fun onBackPressed() {
        //Prevent onBackPressed in home
        TOP_LEVEL_DESTINATIONS.forEach {
            if (navController.currentDestination?.id == it) {
                return
            }
        }
        super.onBackPressed()
    }

    override fun onTabItemClicked(itemSelected: HomeNavigationTab) {
        viewModel.onTabItemClicked(itemSelected)
    }

    private fun setUpObservers() {
        communicationViewModel.uiState.observe(this, communicationObserver)
        viewModel.uiState.observe(this, uiStateObserver)
        navigationViewModel.uiState.observe(this, navigationObserver)
    }

    private fun showBottomNavigation(tabList: List<HomeNavigationTab>) {
        binding.homeNavigation.setItemList(tabList, this)
        navController.navigate(R.id.characterFragment)
    }

    private fun setUpNavigationProperties() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = TOP_LEVEL_DESTINATIONS,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun showCharactersScreen() {
        navController.navigate(R.id.characterFragment)
    }

    private fun showEventsScreen() {
        navController.navigate(R.id.eventsFragment)
    }

    private fun changeToolbarTitle(title: String) {
        binding.toolbar.title = title
    }

    companion object {
        val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.characterFragment,
            R.id.eventsFragment
        )

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {

            }
        }
    }
}