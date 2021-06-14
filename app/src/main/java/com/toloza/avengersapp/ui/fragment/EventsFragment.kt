package com.toloza.avengersapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toloza.avengersapp.R
import com.toloza.avengersapp.data.model.internal.AvengersEventAdapterModel
import com.toloza.avengersapp.databinding.FragmentHomeBinding
import com.toloza.avengersapp.ui.adapter.EventListener
import com.toloza.avengersapp.ui.adapter.EventsAdapter
import com.toloza.avengersapp.ui.viewmodel.EventsViewModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.events.EventsUiModel
import com.toloza.avengersapp.util.EndlessRecyclerViewScrollListener
import com.toloza.avengersapp.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsFragment: BaseFragment(R.layout.fragment_home), EventListener {
    private val viewModel: EventsViewModel by viewModel()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private var adapter: EventsAdapter? = null

    private val uiStateObserver = Observer<EventsUiModel> {
        if (it.showLoading) showLoading() else hideLoading()
        it.showError?.consume()?.let { error -> showError(error) }
        it.showEventsList?.consume()?.let { list -> setUpAdapter(list) }
        it.updateEventsList?.consume()?.let { list -> updateEventsList(list) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner, uiStateObserver)
        viewModel.getEvents()
    }

    override fun onClickEvent(adapterModel: AvengersEventAdapterModel, adapterPosition: Int) {
        //val newList = viewModel.updateItem(adapterPosition)
        adapter?.currentList?.get(adapterPosition)?.apply {
            isExpanded = !isExpanded
        }
        adapter?.notifyItemChanged(adapterPosition)
        //adapter?.submitList(newList)
    }

    private fun setUpAdapter(list: List<AvengersEventAdapterModel>) {
        adapter = EventsAdapter(this)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(
            object :
                EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.getEvents(page)
                }
            })

        adapter?.submitList(list)
    }

    private fun updateEventsList(list: List<AvengersEventAdapterModel>) {
        adapter?.submitList(list)
    }
}