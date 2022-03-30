package com.edna.android.push.demo_x.pushdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.dto.ButtonAction
import com.edna.android.push.demo_x.databinding.ActionItemBinding

class ActionAdapter :
    ListAdapter<ButtonAction, ActionAdapter.ViewHolder>(ActionDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ActionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ButtonAction, position: Int) {
            val name = binding.root.context.getString(R.string.push_details_button, position + 1)
            val text = buildSpannedString {
                append("${item.title}\n")
                underline { append(item.action) }
            }
            binding.action = ButtonActionViewItem(name, text)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ActionItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    data class ButtonActionViewItem(
        val buttonName: String,
        val buttonText: CharSequence
    )
}

class ActionDiffCallback : DiffUtil.ItemCallback<ButtonAction>() {
    override fun areItemsTheSame(oldItem: ButtonAction, newItem: ButtonAction): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    override fun areContentsTheSame(oldItem: ButtonAction, newItem: ButtonAction): Boolean {
        return oldItem == newItem
    }
}