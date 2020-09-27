package ru.mrlargha.englishwords.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mrlargha.englishwords.data.WordWithTranslation
import ru.mrlargha.englishwords.databinding.WordCardBinding

class WordAdapter :
    ListAdapter<WordWithTranslation, WordAdapter.WordViewHolder>(WordDiffCallback()) {

    class WordViewHolder(private val binding: WordCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newWord: WordWithTranslation) {
            binding.apply {
                word.text = newWord.word.englishWord
                translation.text =
                    newWord.translations.joinToString(separator = ", ") {
                        it.translationText
                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            WordCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class WordDiffCallback : DiffUtil.ItemCallback<WordWithTranslation>() {
    override fun areContentsTheSame(
        oldItem: WordWithTranslation,
        newItem: WordWithTranslation
    ): Boolean {
        return oldItem.word.wordId == newItem.word.wordId
    }

    override fun areItemsTheSame(
        oldItem: WordWithTranslation,
        newItem: WordWithTranslation
    ): Boolean {
        return oldItem == newItem
    }
}