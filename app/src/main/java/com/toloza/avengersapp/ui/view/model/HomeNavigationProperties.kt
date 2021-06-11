package com.toloza.avengersapp.ui.view.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface HomeNavigationProperties {
    @StringRes
    fun getName(): Int

    @DrawableRes
    fun getEnabledIcon(): Int

    @DrawableRes
    fun getDisabledIcon(): Int
}
