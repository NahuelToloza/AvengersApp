package com.toloza.avengersapp.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class HomeNavigationModel(
    @StringRes val name: Int,
    @DrawableRes val enabledIcon: Int,
    @DrawableRes val disabledIcon: Int
)