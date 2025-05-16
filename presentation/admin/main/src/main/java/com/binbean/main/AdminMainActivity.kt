package com.binbean.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.binbean.main.databinding.ActivityAdminMainBinding


class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fragmentContainer.post {
            val navController =
                binding.fragmentContainer.getFragment<NavHostFragment>().navController
            binding.bottomNavigationView.setupWithNavController(navController)
        }
    }

}