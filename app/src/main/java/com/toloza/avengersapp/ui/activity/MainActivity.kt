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
import com.toloza.avengersapp.ui.view.HomeNavigationModel
import com.toloza.avengersapp.ui.viewmodel.LoginCommunicationUiModel
import com.toloza.avengersapp.ui.viewmodel.LoginCommunicationViewModel
import com.toloza.avengersapp.ui.viewmodel.MainViewModel
import com.toloza.avengersapp.util.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), HomeNavigationListener {
    private val communicationViewModel by viewModel<LoginCommunicationViewModel>()
    private val viewModel: MainViewModel by inject()

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController

    private val uiModelObserver = Observer<LoginCommunicationUiModel> {
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

        communicationViewModel.uiModel.observe(this, uiModelObserver)
    }

    override fun onTabItemClicked(itemSelected: HomeNavigationModel) {
        viewModel.onTabItemClicked(itemSelected)
    }

    private fun showBottomNavigation(tabList: List<HomeNavigationModel>) {
        binding.homeNavigation.visible()
        binding.homeNavigation.setItemList(tabList, this)
        binding.navHostFragment.findNavController().navigate(R.id.characterFragment)
    }

    private fun setUpNavigationProperties() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {

            }
        }
    }
}