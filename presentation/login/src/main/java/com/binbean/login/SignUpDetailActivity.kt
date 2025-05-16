package com.binbean.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.binbean.login.databinding.ActivitySignUpDetailBinding

class SignUpDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}