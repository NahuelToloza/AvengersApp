package com.toloza.avengersapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toloza.avengersapp.data.model.AvengersEvent
import com.toloza.avengersapp.databinding.ItemEventBinding
import com.toloza.avengersapp.extensions.loadImage
import com.toloza.avengersapp.ui.adapter.model.AvengersEventAdapterModel

class EventsAdapter(
    private val listener: EventListener
) : ListAdapter<AvengersEventAdapterModel, EventsAdapter.EventViewHolder>(updateEventsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(currentList[holder.adapterPosition], listener)
    }

    class EventViewHolder(
        private val binding: ItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(avengersEvent: AvengersEventAdapterModel, listener: EventListener) =
            with(binding) {
                val event = avengersEvent.event
                imgEvent.loadImage(event.imageUrl)
                tvTitle.text = event.title
                tvDescription.text = event.description
                //TODO handle arrow
                //TODO ADD OTHER RECYCLER

                root.setOnClickListener {
                    listener.onClickEvent(event)
                }
            }
    }

    companion object {
        private fun updateEventsDiffCallback() =
            object : DiffUtil.ItemCallback<AvengersEventAdapterModel>() {
                override fun areItemsTheSame(
                    oldItem: AvengersEventAdapterModel,
                    newItem: AvengersEventAdapterModel
                ): Boolean {
                    return oldItem.event.id == newItem.event.id
                }

                override fun areContentsTheSame(
                    oldItem: AvengersEventAdapterModel,
                    newItem: AvengersEventAdapterModel
                ): Boolean {
                    return oldItem.event == newItem.event
                }
            }
    }
}

interface EventListener {
    fun onClickEvent(avengersEvent: AvengersEvent)
}