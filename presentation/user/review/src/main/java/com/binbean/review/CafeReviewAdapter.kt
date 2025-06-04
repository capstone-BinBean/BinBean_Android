package com.binbean.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arieum.review.R
import com.arieum.review.databinding.ReviewItemBinding
import com.binbean.domain.cafe.Review

class CafeReviewAdapter: ListAdapter<Review, CafeReviewAdapter.ReviewViewHolder>(DiffCallback()) {

    inner class ReviewViewHolder(private val binding: ReviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.tvDate.text = review.date
            binding.tvNickname.text = review.nickname
            binding.tvReviewText.text = review.reviewText

            binding.tvMyReviewTag.visibility = if (review.isMyReview) View.VISIBLE else View.GONE

            val starViews = listOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)
            for (i in 0 until 5) {
                starViews[i].setImageResource(
                    if (i < review.reviewScore) R.drawable.ic_filled_star
                    else R.drawable.ic_empty_star
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.reviewText == newItem.reviewText
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }
    }
}