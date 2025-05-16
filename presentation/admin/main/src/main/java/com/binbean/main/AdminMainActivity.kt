package com.binbean.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.binbean.home.AdminHomeFragment
import com.binbean.main.databinding.ActivityAdminMainBinding
import com.binbean.register.AdminRegisterFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


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