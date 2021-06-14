package com.toloza.avengersapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toloza.avengersapp.data.model.internal.ComicModel
import com.toloza.avengersapp.databinding.HeaderCharacterDetailBinding
import com.toloza.avengersapp.databinding.ItemComicsBinding
import com.toloza.avengersapp.extensions.gone
import com.toloza.avengersapp.extensions.loadImage
import com.toloza.avengersapp.extensions.visible
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.CharacterDetailHeaderModel
import com.toloza.avengersapp.ui.viewmodel.uimodel.detail.ComicsAdapterModel

class ComicsAdapter(
    private val comicsAdapterModel: ComicsAdapterModel,
    private val listener: ComicsListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val headerCount = getHeaderCount()

    override fun getItemViewType(position: Int): Int {
        comicsAdapterModel.headerModel?.let {
            if (position == 0) {
                return ComicAdapterViewType.HEADER.ordinal
            }
            return ComicAdapterViewType.ITEM.ordinal
        } ?: run {
            return ComicAdapterViewType.ITEM.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ComicAdapterViewType.HEADER.ordinal -> {
                val binding = HeaderCharacterDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ComicHeaderViewHolder(binding)
            }
            else -> {
                val binding = ItemComicsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ComicViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicHeaderViewHolder -> {
                holder.bind(comicsAdapterModel.headerModel!!)
            }
            is ComicViewHolder -> {
                holder.bind(
                    comicsAdapterModel.comics[holder.adapterPosition - headerCount],
                    listener
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return comicsAdapterModel.comics.size + headerCount
    }

    private fun getHeaderCount(): Int {
        return comicsAdapterModel.headerModel?.let { 1 } ?: run { 0 }
    }

    class ComicViewHolder(
        private val binding: ItemComicsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comicModel: ComicModel, listener: ComicsListener?) = with(binding) {
            tvTitle.text = comicModel.name
            tvDescription.text = comicModel.year

            listener?.let { listener ->
                binding.root.setOnClickListener { listener.onClickItem() }
            }
        }
    }

    class ComicHeaderViewHolder(
        private val binding: HeaderCharacterDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(headerModel: CharacterDetailHeaderModel) = with(binding) {
            if (headerModel.description.isBlank()) {
                binding.tvDescription.gone()
            } else {
                binding.tvDescription.visible()
                binding.tvDescription.text = headerModel.description
            }
            binding.imgCharacter.loadImage(headerModel.imageUrl)
        }
    }
}

enum class ComicAdapterViewType {
    HEADER,
    ITEM
}

interface ComicsListener {
    fun onClickItem()
}