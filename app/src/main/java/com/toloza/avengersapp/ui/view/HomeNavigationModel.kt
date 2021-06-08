package com.toloza.avengersapp.ui.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.toloza.avengersapp.R

data class HomeNavigationModel(
    @StringRes val name: Int,
    @DrawableRes val enabledIcon: Int,
    @DrawableRes val disabledIcon: Int
) {
    fun isCharacter() = (name == R.string.characters)

    fun isEvent() = (name == R.string.events)

    companion object {
        fun getTabList(): List<HomeNavigationModel> {
            return listOf(
                HomeNavigationModel(
                    R.string.characters,
                    R.drawable.ic_character_enabled,
                    R.drawable.ic_character_disabled
                ),
                HomeNavigationModel(
                    R.string.events,
                    R.drawable.ic_events_enabled,
                    R.drawable.ic_events_disabled
                )
            )
        }
    }
}