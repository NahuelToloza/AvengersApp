package com.toloza.avengersapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.toloza.avengersapp.databinding.ItemBottomNavigationBinding
import com.toloza.avengersapp.extensions.setTopDrawable


class HomeNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    var selectedPosition = -1

    init {
        orientation = HORIZONTAL
    }

    fun setItemList(list: List<HomeNavigationModel>) {
        list.forEachIndexed { position, item ->
            val binding = ItemBottomNavigationBinding.inflate(
                LayoutInflater.from(context),
                this,
                false
            )
            binding.tabTitle.text = context.getString(item.name)
            binding.tabTitle.setTopDrawable(item.disabledIcon)
            binding.parentLayout.setOnClickListener {
                val beforeSelectedText =
                    (this.getChildAt(selectedPosition) as? ViewGroup)?.getChildAt(0)
                            as? AppCompatTextView
                beforeSelectedText?.setTopDrawable(list[selectedPosition].disabledIcon)
                binding.tabTitle.setTopDrawable(item.enabledIcon)
                selectedPosition = position
            }

            val param = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                1.0f
            )
            binding.root.layoutParams = param

            this.addView(binding.root)
        }
    }
}

data class HomeNavigationModel(
    @StringRes val name: Int,
    @DrawableRes val enabledIcon: Int,
    @DrawableRes val disabledIcon: Int
)