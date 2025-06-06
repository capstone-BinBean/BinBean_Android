package com.binbean.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.binbean.review.databinding.ActivityReviewDetailBinding

class ReviewDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}