package com.toloza.avengersapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.toloza.avengersapp.R
import com.toloza.avengersapp.databinding.ActivityLoginBinding
import com.toloza.avengersapp.extensions.setToolbar
import com.toloza.avengersapp.util.viewBinding

class LoginActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setToolbar(
            binding.toolbarLayout.toolbar,
            binding.toolbarLayout.title,
            getString(R.string.toolbar_title)
        )
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java).apply {

            }
        }
    }
}