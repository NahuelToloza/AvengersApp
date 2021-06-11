package com.toloza.avengersapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.toloza.avengersapp.databinding.ItemBottomNavigationBinding
import com.toloza.avengersapp.extensions.setTopDrawable
import com.toloza.avengersapp.ui.view.model.HomeNavigationTab

class HomeNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    var selectedPosition = 0

    init {
        orientation = HORIZONTAL
    }

    /**
     * @param list is a List with representative tab items
     * @param listener is a interface for itemClicks
     */
    fun setItemList(list: List<HomeNavigationTab>, listener: HomeNavigationListener) {
        list.forEachIndexed { position, item ->
            val binding = ItemBottomNavigationBinding.inflate(
                LayoutInflater.from(context),
                this,
                false
            )
            binding.tabTitle.text = context.getString(item.getName())
            binding.tabTitle.setTopDrawable(item.getDisabledIcon())

            if (position == 0) {
                binding.tabTitle.setTopDrawable(item.getEnabledIcon())
                selectedPosition = 0
            }

            binding.tabTitle.setOnClickListener {
                //Disabled before item
                val beforeSelectedText = getBeforeSelectedText()
                beforeSelectedText?.setTopDrawable(list[selectedPosition].getDisabledIcon())

                //Enabled selected item
                binding.tabTitle.setTopDrawable(item.getEnabledIcon())
                selectedPosition = position
                listener.onTabItemClicked(item)
            }

            binding.root.layoutParams = getSameWeightParam()

            this.addView(binding.root)
        }
    }

    private fun getBeforeSelectedText() =
        (this.getChildAt(selectedPosition) as? AppCompatTextView)

    private fun getSameWeightParam(): ViewGroup.LayoutParams {
        return LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT,
            DEFAULT_WEIGHT
        )
    }

    companion object {
        private const val DEFAULT_WEIGHT = 1.0f
    }
}

interface HomeNavigationListener {
    fun onTabItemClicked(itemSelected: HomeNavigationTab)
}