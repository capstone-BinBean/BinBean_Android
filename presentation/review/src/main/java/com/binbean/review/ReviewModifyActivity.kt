package com.binbean.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.binbean.review.databinding.ActivityReviewModifyBinding

class ReviewModifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}