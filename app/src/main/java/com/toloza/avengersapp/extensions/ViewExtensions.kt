package com.toloza.avengersapp.extensions

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide

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
