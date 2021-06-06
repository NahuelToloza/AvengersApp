package com.toloza.avengersapp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.toloza.avengersapp.R
import com.toloza.avengersapp.data.login.LoginBuilder
import com.toloza.avengersapp.ui.viewmodel.MainUiModel
import com.toloza.avengersapp.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()

    private val resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.handleSuccessLogin()
        } else {
            viewModel.handleFailedLogin(getString(R.string.login_error_message))
        }
    }

    private val uiModelObserver = Observer<MainUiModel> {
        it.launchLogin?.consume()?.let { loginBuilder -> launchLogin(loginBuilder) }
        it.continueWithFlow?.consume()?.let { continueWithFlow() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel.setUpUserInformation()
        viewModel.uiModel.observe(this, uiModelObserver)
    }

    private fun launchLogin(loginBuilder: LoginBuilder) {
        val intent = loginBuilder.build()
        resultLauncher.launch(intent)
    }

    private fun continueWithFlow() {
        //TODO CONTINUAR COMO DEBERIA
        Log.d("continueWithFlow", "Si papaaaaaa")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {

            }
        }
    }
}