package com.toloza.avengersapp.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.toloza.avengersapp.R

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun AppCompatTextView.setTopDrawable(@DrawableRes resource: Int){
    this.setCompoundDrawablesWithIntrinsicBounds(0, resource, 0, 0)
}

fun ImageView.loadImage(imageUrl: String){
    Glide.with(context).load(imageUrl).into(this)
}

fun Context.getActualColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}