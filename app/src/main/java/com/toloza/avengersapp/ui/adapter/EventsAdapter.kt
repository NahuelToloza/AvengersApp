package com.toloza.avengersapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toloza.avengersapp.R
import com.toloza.avengersapp.data.model.internal.AvengersEventAdapterModel
import com.toloza.avengersapp.databinding.ItemEventBinding
import com.toloza.avengersapp.extensions.gone
import com.toloza.avengersapp.extensions.loadImage
import com.toloza.avengersapp.extensions.visible

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
        fun bind(adapterModel: AvengersEventAdapterModel, listener: EventListener) =
            with(binding) {
                val event = adapterModel.event
                imgEvent.loadImage(event.imageUrl)
                tvTitle.text = event.title
                tvDescription.text = event.description
                //TODO Change description for date

                if (adapterModel.isExpanded) {
                    imgArrow.setImageResource(R.drawable.ic_up_arrow)

                    binding.groupComics.visible()
                    binding.recyclerView.setRecycledViewPool(this.recyclerView.recycledViewPool)
                    binding.recyclerView.adapter = ComicsAdapter(adapterModel.comicsAdapterModel, object : ComicsListener {
                        override fun onClickItem() {
                            listener.onClickEvent(adapterModel, adapterPosition)
                        }
                    })
                } else {
                    imgArrow.setImageResource(R.drawable.ic_down_arrow)
                    binding.groupComics.gone()
                }

                root.setOnClickListener {
                    listener.onClickEvent(adapterModel, adapterPosition)
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
                    return oldItem.event == newItem.event &&
                            oldItem.isExpanded == newItem.isExpanded
                }
            }
    }
}

interface EventListener {
    fun onClickEvent(adapterModel: AvengersEventAdapterModel, adapterPosition: Int)
}