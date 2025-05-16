package com.binbean.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.binbean.container.UserMainActivity
import com.binbean.login.databinding.ActivitySignUpWayBinding
import com.binbean.main.AdminMainActivity

class SignUpWayActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpWayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpWayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userIcon.setOnClickListener {
            val intent = Intent(
                this,
                UserMainActivity::class.java
            )
            startActivity(intent)
        }

        binding.managerIcon.setOnClickListener {
            val intent = Intent(
                this,
                AdminMainActivity::class.java
            )
            startActivity(intent)
        }

        binding.emailSignUp.setOnClickListener {
            val intent = Intent(
                this,
                SignUpDetailActivity::class.java
            )
            startActivity(intent)
        }


    }
}