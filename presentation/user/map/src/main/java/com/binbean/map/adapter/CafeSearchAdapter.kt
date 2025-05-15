package com.binbean.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binbean.domain.cafe.Cafe
import com.binbean.map.databinding.SearchCafeItemBinding

class CafeSearchAdapter : ListAdapter<Cafe, CafeSearchAdapter.CafeViewHolder>(
    DiffCallback()
){
    var onItemClick: ((Cafe) -> Unit)? = null

    inner class CafeViewHolder(private val binding: SearchCafeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cafe) {
            binding.apply {
                binding.cafeName.text = item.name
                binding.cafeAddress.text = item.address
                binding.root.setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeViewHolder {
        val binding = SearchCafeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CafeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Cafe>() {
        override fun areItemsTheSame(oldItem: Cafe, newItem: Cafe): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Cafe, newItem: Cafe): Boolean {
            return oldItem == newItem
        }
    }
}