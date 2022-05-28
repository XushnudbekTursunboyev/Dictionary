package com.example.dictonary.ui.word

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictonary.R
import com.example.dictonary.databinding.ItemWordsBinding
import com.example.dictonary.model.room.entity.Word

class WordAdapter(val onClick: (word: Word) -> Unit) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private var items = ArrayList<Word>()

    inner class ViewHolder(private val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: Word) {
            binding.apply {

                tvWord.text = word.en
                if (word.favorite) {
                    ivFavorite.setImageResource(R.drawable.ic_baseline_star_24)
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
            }

            binding.root.setOnClickListener {
                onClick(word)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun submitList(list: List<Word>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size
}