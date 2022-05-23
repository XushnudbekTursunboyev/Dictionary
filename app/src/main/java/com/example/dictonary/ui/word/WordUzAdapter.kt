package com.example.dictonary.ui.word

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.navArgument
import androidx.recyclerview.widget.RecyclerView
import com.example.dictonary.databinding.ItemWordsBinding
import com.example.dictonary.model.room.entity.Word
import com.example.dictonary.ui.home.HomeFragment

class WordUzAdapter(
    var list: List<Word>,
    val onClick:(word: Word)-> Unit
) : RecyclerView.Adapter<WordUzAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(word: Word){
            binding.apply {
                tvWord.text = word.uz
            }

            binding.root.setOnClickListener {
                onClick(word)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}