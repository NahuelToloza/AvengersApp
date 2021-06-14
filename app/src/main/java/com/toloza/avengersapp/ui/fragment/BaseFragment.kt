package com.toloza.avengersapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.toloza.avengersapp.R
import com.toloza.avengersapp.data.model.core.ServerError
import com.toloza.avengersapp.extensions.gone
import com.toloza.avengersapp.extensions.visible
import com.toloza.avengersapp.ui.viewmodel.NavigationCommunicationViewModel

open class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {
    private var loadingView: View? = null

    protected val navigationViewModel by activityViewModels<NavigationCommunicationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingView = activity?.findViewById(R.id.circular_progress_indicator)

        navigationViewModel.showNavigation(shouldShowNavigation())
    }

    fun showError(error: ServerError) {
        Toast.makeText(context, error.getErrorMessage(), Toast.LENGTH_LONG).show()
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

    protected open fun shouldShowNavigation(): Boolean = true
}
