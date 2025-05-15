package com.binbean.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binbean.domain.cafe.Cafe

class BookmarkedCafeListAdapter: ListAdapter<Cafe, BookmarkedCafeListAdapter.BookmarkedCafeViewHolder>(DiffCallback()) {

    inner class BookmarkedCafeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tv_cafe_name)
        private val address: TextView = view.findViewById(R.id.tv_cafe_address)
        private val statusIcon: ImageView = view.findViewById(R.id.iv_status)
        private val statusText: TextView = view.findViewById(R.id.tv_status)
        private val seatRecyclerView: RecyclerView = view.findViewById(R.id.rv_seats)

        fun bind(item: Cafe) {
            name.text = item.name
            address.text = item.address
            statusIcon.setImageResource(item.status.toIconRes())
            statusText.text = item.status.toStatusText()
            statusText.setTextColor(item.status.toStatusColor(itemView.context))

            val seatAdapter = SeatListAdapter()
            seatRecyclerView.adapter = seatAdapter
            seatAdapter.submitList(item.seats)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkedCafeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cafe, parent, false)
        return BookmarkedCafeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkedCafeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Cafe>() {
        override fun areItemsTheSame(old: Cafe, new: Cafe) = old.id == new.id
        override fun areContentsTheSame(old: Cafe, new: Cafe) = old == new
    }
}