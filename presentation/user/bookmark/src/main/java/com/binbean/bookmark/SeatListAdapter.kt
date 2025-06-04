package com.binbean.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binbean.domain.SeatResponse

class SeatListAdapter  : ListAdapter<SeatResponse, SeatListAdapter.SeatViewHolder>(DiffCallback()) {

    inner class SeatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val seatNumber: TextView = view.findViewById(R.id.tv_seat_number)
        private val statusTv: TextView = view.findViewById(R.id.status_tv)

        fun bind(item: SeatResponse) {
            seatNumber.text = "${item.seatsNumber}번"
            val isAvailable = item.seatsAvailable == 1

            statusTv.text = if ( isAvailable ) "사용가능" else "사용중"
            statusTv.isSelected = !isAvailable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat, parent, false)
        return SeatViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<SeatResponse>() {
        override fun areItemsTheSame(old: SeatResponse, new: SeatResponse) = old.seatsNumber == new.seatsNumber
        override fun areContentsTheSame(old: SeatResponse, new: SeatResponse) = old == new
    }
}