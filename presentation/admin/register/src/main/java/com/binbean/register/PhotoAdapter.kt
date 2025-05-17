package com.binbean.register

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binbean.register.databinding.AddPhotoFooterBinding
import com.binbean.register.databinding.AddPhotoItemBinding
import com.bumptech.glide.Glide

class PhotoAdapter(
    private val items: List<Uri>,
    private val onClick: () -> Unit,
    private val onDelete: ((Int) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == TYPE_FOOTER) {
            val binding = AddPhotoFooterBinding.inflate(inflater, parent, false)
            FooterViewHolder(binding)
        } else {
            val binding = AddPhotoItemBinding.inflate(inflater, parent, false)
            ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder && position <= items.size) {
            holder.bind(items[position])
        } else {
            holder.itemView.setOnClickListener {
                onClick()
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size) TYPE_FOOTER else TYPE_ITEM
    }

    inner class ItemViewHolder(private val binding: AddPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            Glide.with(binding.photo.context)
                .load(uri)
                .centerCrop()
                .into(binding.photo)

            binding.delBtn.setOnClickListener {
                onDelete(bindingAdapterPosition)
            }
        }
    }

    inner class FooterViewHolder(private val binding: AddPhotoFooterBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_FOOTER = 1
    }

}