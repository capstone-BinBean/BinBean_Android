package com.binbean.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binbean.domain.cafe.CafeInfoImgItem
import com.binbean.map.databinding.CafeIntoImgItemBinding
import com.bumptech.glide.Glide

class CafeInfoImgAdapter : ListAdapter<CafeInfoImgItem, CafeInfoImgAdapter.CafeInfoImgViewHolder>(DiffCallback()) {

    inner class CafeInfoImgViewHolder(private val binding: CafeIntoImgItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CafeInfoImgItem) {
            Glide.with(binding.root)
                .load(item.imageUrl)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeInfoImgViewHolder {
        val binding = CafeIntoImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CafeInfoImgViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CafeInfoImgViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CafeInfoImgItem>() {
        override fun areItemsTheSame(oldItem: CafeInfoImgItem, newItem: CafeInfoImgItem): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: CafeInfoImgItem, newItem: CafeInfoImgItem): Boolean {
            return oldItem == newItem
        }
    }
}