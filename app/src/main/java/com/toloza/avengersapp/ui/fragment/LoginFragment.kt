package com.toloza.avengersapp.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.toloza.avengersapp.R
import com.toloza.avengersapp.data.login.LoginBuilder
import com.toloza.avengersapp.ui.viewmodel.LoginCommunicationViewModel
import com.toloza.avengersapp.ui.viewmodel.LoginUiModel
import com.toloza.avengersapp.ui.viewmodel.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by inject()
    private val communicationViewModel: LoginCommunicationViewModel by sharedViewModel()

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.handleSuccessLogin()
            } else {
                viewModel.handleFailedLogin(getString(R.string.login_error_message))
            }
        }

    private val uiModelObserver = Observer<LoginUiModel> {
        it.launchLogin?.consume()?.let { loginBuilder -> launchLogin(loginBuilder) }
        it.continueWithFlow?.consume()?.let { continueWithFlow() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setUpUserInformation()
        viewModel.uiModel.observe(viewLifecycleOwner, uiModelObserver)
    }

    private fun launchLogin(loginBuilder: LoginBuilder) {
        val intent = loginBuilder.build()
        resultLauncher.launch(intent)
    }

    private fun continueWithFlow() {
        communicationViewModel.notifyLoginSuccess()
    }
}