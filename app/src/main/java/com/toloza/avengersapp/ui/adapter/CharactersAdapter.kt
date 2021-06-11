package com.toloza.avengersapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toloza.avengersapp.R
import com.toloza.avengersapp.databinding.ItemCharacterBinding
import com.toloza.avengersapp.ui.adapter.model.CharacterAdapterModel

class CharactersAdapter(
    private val listener: CharacterListener
) : ListAdapter<CharacterAdapterModel, CharactersAdapter.CharacterViewHolder>(createGroupsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(currentList[holder.adapterPosition], listener)
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCharacterBinding.bind(itemView)

        fun bind(model: CharacterAdapterModel, listener: CharacterListener) = with(binding) {
            Glide.with(itemView.context).load(model.imageUrl).into(imgCharacter)

            tvTitle.text = model.title
            tvDescription.text = model.description
            root.setOnClickListener {
                listener.onClickCharacterItem(model)
            }
        }
    }

    companion object {
        private fun createGroupsDiffCallback() = object : DiffUtil.ItemCallback<CharacterAdapterModel>() {
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