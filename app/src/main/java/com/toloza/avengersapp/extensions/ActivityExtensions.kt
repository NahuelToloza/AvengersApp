package com.toloza.avengersapp.extensions

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun AppCompatActivity.setToolbar(
    toolbar: Toolbar,
    titleView: TextView,
    title: String,
    showBackArrow: Boolean = false
) {
    titleView.text = title
    toolbar.title = ""
    setSupportActionBar(toolbar)
    if (showBackArrow) {
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}