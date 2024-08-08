package com.capstone.project.testsuitmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.project.testsuitmedia.data.remote.response.DataItem
import com.capstone.project.testsuitmedia.databinding.ListItemBinding

class UserAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<DataItem, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(user: DataItem)
    }

    inner class UserViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataItem) {
            Glide.with(binding.root.context)
                .load(user.avatar)
                .into(binding.avatarImageView)
            binding.nameTextView.text = "${user.firstName} ${user.lastName}"
            binding.emailTextView.text = user.email
            binding.root.setOnClickListener { itemClickListener.onItemClick(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
