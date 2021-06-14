package com.toloza.avengersapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toloza.avengersapp.data.model.internal.CharacterAdapterModel
import com.toloza.avengersapp.databinding.ItemCharacterBinding
import com.toloza.avengersapp.extensions.loadImage

class CharactersAdapter(
    private val listener: CharacterListener
) : ListAdapter<CharacterAdapterModel, CharactersAdapter.CharacterViewHolder>(updateCharactersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(currentList[holder.adapterPosition], listener)
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CharacterAdapterModel, listener: CharacterListener) = with(binding) {
            imgCharacter.loadImage(model.imageUrl)

            tvTitle.text = model.title
            tvDescription.text = model.description
            root.setOnClickListener {
                listener.onClickCharacterItem(model)
            }
        }
    }

    companion object {
        private fun updateCharactersDiffCallback() = object : DiffUtil.ItemCallback<CharacterAdapterModel>() {
            override fun areItemsTheSame(oldItem: CharacterAdapterModel, newItem: CharacterAdapterModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterAdapterModel, newItem: CharacterAdapterModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface CharacterListener {
    fun onClickCharacterItem(characterAdapterModel: CharacterAdapterModel)
}