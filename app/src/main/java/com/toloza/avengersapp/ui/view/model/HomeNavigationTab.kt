package com.toloza.avengersapp.ui.view.model

import com.toloza.avengersapp.R

enum class HomeNavigationTab : HomeNavigationProperties {
    CHARACTERS {
        override fun getName(): Int = R.string.characters

        override fun getEnabledIcon(): Int = R.drawable.ic_character_enabled

        override fun getDisabledIcon(): Int = R.drawable.ic_character_disabled
    },

    EVENTS {
        override fun getName(): Int = R.string.events

        override fun getEnabledIcon(): Int = R.drawable.ic_events_enabled

        override fun getDisabledIcon(): Int = R.drawable.ic_events_disabled
    };

    companion object {
        fun getTabList(): List<HomeNavigationTab> {
            return values().toList()
        }
    }
}