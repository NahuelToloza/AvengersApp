package com.toloza.avengersapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toloza.avengersapp.R
import com.toloza.avengersapp.data.model.internal.CharacterAdapterModel
import com.toloza.avengersapp.databinding.FragmentHomeBinding
import com.toloza.avengersapp.ui.adapter.CharacterListener
import com.toloza.avengersapp.ui.adapter.CharactersAdapter
import com.toloza.avengersapp.ui.viewmodel.CharactersUiModel
import com.toloza.avengersapp.ui.viewmodel.CharactersViewModel
import com.toloza.avengersapp.util.EndlessRecyclerViewScrollListener
import com.toloza.avengersapp.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment(R.layout.fragment_home), CharacterListener {
    private val viewModel: CharactersViewModel by viewModel()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private var adapter: CharactersAdapter? = null

    private val uiStateObserver = Observer<CharactersUiModel> {
        if (it.showLoading) showLoading() else hideLoading()
        it.showCharactersList?.consume()?.let { list -> setUpAdapter(list) }
        it.updateCharactersList?.consume()?.let { list -> updateCharactersList(list) }
        it.showError?.consume()?.let { error -> showError(error) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner, uiStateObserver)
        viewModel.getCharacters()
    }

    override fun onClickCharacterItem(characterAdapterModel: CharacterAdapterModel) {
        val action = CharactersFragmentDirections.actionCharacterFragmentToCharacterDetail(characterAdapterModel)
        findNavController().navigate(action)
    }

    private fun setUpAdapter(list: List<CharacterAdapterModel>) {
        adapter = CharactersAdapter(this)
        binding.recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(
            object :
                EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.getCharacters(page)
                }
            })

        adapter?.submitList(list)
    }

    private fun updateCharactersList(list: List<CharacterAdapterModel>) {
        adapter?.submitList(list)
    }
}