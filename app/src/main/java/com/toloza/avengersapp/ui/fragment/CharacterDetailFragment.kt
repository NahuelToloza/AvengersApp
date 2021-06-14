package com.toloza.avengersapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.toloza.avengersapp.R
import com.toloza.avengersapp.databinding.FragmentHomeBinding
import com.toloza.avengersapp.ui.adapter.ComicsAdapter
import com.toloza.avengersapp.ui.viewmodel.CharacterDetailViewModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.ComicsAdapterModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.CharacterDetailUiModel
import com.toloza.avengersapp.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : BaseFragment(R.layout.fragment_home) {
    private val args: CharacterDetailFragmentArgs by navArgs()

    private val viewModel: CharacterDetailViewModel by viewModel()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val uiStateObserver = Observer<CharacterDetailUiModel> {
        if (it.showLoading) showLoading() else hideLoading()
        it.showError?.consume()?.let { error -> showError(error) }
        it.showInfoDetail?.consume()?.let { detailModel -> showInfoDetail(detailModel) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.character?.title?.let {
            navigationViewModel.changeToolbarTitle(it)
        }
        viewModel.uiState.observe(viewLifecycleOwner, uiStateObserver)
        viewModel.setUpDetail(args.character)
    }

    override fun shouldShowNavigation(): Boolean {
        return false
    }

    private fun showInfoDetail(detailModel: ComicsAdapterModel) {
        binding.recyclerView.adapter = ComicsAdapter(detailModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }
}