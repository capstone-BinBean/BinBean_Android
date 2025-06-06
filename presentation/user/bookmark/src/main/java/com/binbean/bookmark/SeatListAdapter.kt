package com.binbean.bookmark

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binbean.domain.cafe.Seat

class SeatListAdapter  : ListAdapter<Seat, SeatListAdapter.SeatViewHolder>(DiffCallback()) {

    inner class SeatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val seatNumber: TextView = view.findViewById(R.id.tv_seat_number)
        private val btnStatus: Button = view.findViewById(R.id.btn_status)

        fun bind(item: Seat) {
            seatNumber.text = "${item.number}번"
            btnStatus.text = if (item.isAvailable) "사용가능" else "사용중"

            btnStatus.setBackgroundTintList(
                ColorStateList.valueOf(
                if (item.isAvailable) Color.parseColor("#F9FFE6") else Color.parseColor("#EAEAEA")
            ))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat, parent, false)
        return SeatViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Seat>() {
        override fun areItemsTheSame(old: Seat, new: Seat) = old.number == new.number
        override fun areContentsTheSame(old: Seat, new: Seat) = old == new
    }
}