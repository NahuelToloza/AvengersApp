package com.toloza.avengersapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toloza.avengersapp.data.model.AvengersEvent
import com.toloza.avengersapp.data.model.Character
import com.toloza.avengersapp.data.model.Comic
import com.toloza.avengersapp.databinding.ItemCharacterBinding
import com.toloza.avengersapp.extensions.loadImage

class CharactersAdapter(
    private val listener: CharacterListener
) : ListAdapter<Character, CharactersAdapter.CharacterViewHolder>(updateCharactersDiffCallback()) {

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
        fun bind(model: Character, listener: CharacterListener) = with(binding) {
            imgCharacter.loadImage(model.imageUrl)

            tvTitle.text = model.title
            tvDescription.text = model.description
            root.setOnClickListener {
                listener.onClickCharacterItem(model)
            }
        }
    }

    companion object {
        private fun updateCharactersDiffCallback() = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface CharacterListener {
    fun onClickCharacterItem(character: Character)
}