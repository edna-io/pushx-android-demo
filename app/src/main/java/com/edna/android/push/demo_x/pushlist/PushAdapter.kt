package com.edna.android.push.demo_x.pushlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.databinding.PushItemBinding


class PushAdapter(private val viewModel: PushListViewModel) :
    ListAdapter<Push, PushAdapter.ViewHolder>(PushDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: PushItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: PushListViewModel, item: Push) {
            binding.viewmodel = viewModel
            binding.push = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PushItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class PushDiffCallback : DiffUtil.ItemCallback<Push>() {
    override fun areItemsTheSame(oldItem: Push, newItem: Push): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    override fun areContentsTheSame(oldItem: Push, newItem: Push): Boolean {
        return oldItem == newItem
    }
}