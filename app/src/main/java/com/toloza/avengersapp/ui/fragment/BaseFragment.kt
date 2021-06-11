package com.toloza.avengersapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.toloza.avengersapp.R
import com.toloza.avengersapp.extensions.gone
import com.toloza.avengersapp.extensions.visible

open class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {
    private var loadingView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingView = activity?.findViewById(R.id.circular_progress_indicator)
    }

    fun showLoading() {
        loadingView?.visible()
    }

    fun hideLoading() {
        loadingView?.gone()
    }

    override fun onDestroyView() {
        loadingView = null
        super.onDestroyView()
    }
}
