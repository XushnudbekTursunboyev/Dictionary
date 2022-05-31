package com.example.dictonary.ui.word

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictonary.R
import com.example.dictonary.databinding.ItemWordsBinding
import com.example.dictonary.model.room.entity.Word
import com.example.dictonary.utils.CONSTANTS
import com.example.dictonary.utils.SingleBlock

class WordAdapter(val type: String) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private var items = ArrayList<Word>()

    private var listener: SingleBlock<Word>? = null

    inner class ViewHolder(private val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: Word) {
            binding.apply {
                tvWord.text = if (type == CONSTANTS.EN) word.en else word.uz
                ivFavorite.setImageResource(if (word.favorite) R.drawable.ic_select_favorite else R.drawable.ic_unselect_favorite)

                root.setOnClickListener {
                    listener?.invoke(word)
                }
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

    fun setOnCLickListener(block: SingleBlock<Word>) {
        listener = block
    }

    override fun getItemCount(): Int = items.size
}