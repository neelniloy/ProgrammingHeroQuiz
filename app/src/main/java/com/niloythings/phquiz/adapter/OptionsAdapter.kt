package com.niloythings.phquiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.niloythings.phquiz.databinding.OptionItemViewBinding
import com.niloythings.phquiz.models.Answer

class OptionsAdapter : ListAdapter<Answer, OptionsAdapter.QuizViewHolder>(
   QuizDiffUtil()
){

    class QuizViewHolder(val binding: OptionItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Answer) {
            binding.item = item
        }
    }

    class QuizDiffUtil : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(
            oldItem: Answer,
            newItem: Answer
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Answer,
            newItem: Answer
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = OptionItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        //set card background

    }
}