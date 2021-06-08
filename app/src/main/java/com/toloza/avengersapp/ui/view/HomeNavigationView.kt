package com.toloza.avengersapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
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

    /**
     * @param List<HomeNavigationModel> List with representative tab items
     */
    fun setItemList(list: List<HomeNavigationModel>) {
        list.forEachIndexed { position, item ->
            val binding = ItemBottomNavigationBinding.inflate(
                LayoutInflater.from(context),
                this,
                false
            )
            binding.tabTitle.text = context.getString(item.name)
            binding.tabTitle.setTopDrawable(item.disabledIcon)

            binding.tabTitle.setOnClickListener {
                //Disabled before item
                val beforeSelectedText = getBeforeSelectedText()
                beforeSelectedText?.setTopDrawable(list[selectedPosition].disabledIcon)

                //Enabled selected item
                binding.tabTitle.setTopDrawable(item.enabledIcon)
                selectedPosition = position
            }

            binding.root.layoutParams = getSameWeightParam()

            this.addView(binding.root)
        }
    }

    private fun getBeforeSelectedText() =
        (this.getChildAt(selectedPosition) as? ViewGroup)?.getChildAt(0) as? AppCompatTextView

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