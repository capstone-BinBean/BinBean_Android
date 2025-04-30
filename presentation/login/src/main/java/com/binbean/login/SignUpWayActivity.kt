package com.binbean.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.binbean.login.databinding.ActivitySignUpWayBinding

class SignUpWayActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpWayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpWayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}