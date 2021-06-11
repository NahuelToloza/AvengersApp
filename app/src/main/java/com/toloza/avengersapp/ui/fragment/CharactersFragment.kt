package com.toloza.avengersapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toloza.avengersapp.R
import com.toloza.avengersapp.databinding.FragmentCharacterBinding
import com.toloza.avengersapp.ui.adapter.CharacterListener
import com.toloza.avengersapp.ui.adapter.CharactersAdapter
import com.toloza.avengersapp.ui.adapter.model.Character
import com.toloza.avengersapp.ui.viewmodel.CharactersUiModel
import com.toloza.avengersapp.ui.viewmodel.CharactersViewModel
import com.toloza.avengersapp.util.EndlessRecyclerViewScrollListener
import com.toloza.avengersapp.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment(R.layout.fragment_character), CharacterListener {
    private val viewModel: CharactersViewModel by viewModel()

    private val binding by viewBinding(FragmentCharacterBinding::bind)

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

    override fun onClickCharacterItem(character: Character) {
        val bundle = bundleOf("character" to character)
        findNavController().navigate(R.id.characterDetail, bundle)
    }

    private fun setUpAdapter(list: List<Character>) {
        adapter = CharactersAdapter(this)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(
            object :
                EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.getCharacters(page)
                }
            })

        adapter?.submitList(list)
    }

    private fun updateCharactersList(list: List<Character>) {
        adapter?.submitList(list)
    }
}