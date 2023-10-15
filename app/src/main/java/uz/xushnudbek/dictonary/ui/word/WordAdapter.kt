package uz.xushnudbek.dictonary.ui.word

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictonary.R
import com.example.dictonary.databinding.ItemWordsBinding
import uz.xushnudbek.dictonary.model.room.entity.WordModel
import uz.xushnudbek.dictonary.utils.CONSTANTS
import uz.xushnudbek.dictonary.utils.SingleBlock

class WordAdapter(val type: String) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private var items = ArrayList<WordModel>()

    private var listener: SingleBlock<WordModel>? = null

    private var sound: SingleBlock<WordModel>? = null

    inner class ViewHolder(private val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: WordModel) {
            binding.apply {
                tvWord.text = if (type == CONSTANTS.EN) word.english else word.uzbek
                ivFavorite.setImageResource(if (word.isFavourite==1) R.drawable.ic_select_favorite else R.drawable.ic_unselect_favorite)

                root.setOnClickListener {
                    listener?.invoke(word)
                }

                ivVoice.setOnClickListener {
                    sound?.invoke(word)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun submitList(list: List<WordModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    fun setOnCLickListener(block: SingleBlock<WordModel>) {
        listener = block
    }

    fun clickSound(block: SingleBlock<WordModel>){
        sound = block
    }

    override fun getItemCount(): Int = items.size
}