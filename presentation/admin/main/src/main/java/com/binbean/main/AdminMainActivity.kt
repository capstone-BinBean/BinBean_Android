package com.binbean.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.binbean.main.databinding.ActivityAdminMainBinding
import com.binbean.navigation.R


class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }

    /**
     * 네비게이션 초기화
     */
    private fun initNavigation() {
        val navController =
            binding.fragmentContainer.getFragment<NavHostFragment>().navController
        binding.bottomNavigationView.setupWithNavController(navController)

        // 네비게이션을 숨기는 프래그먼트
        val hideNavDestinations = setOf(
            R.id.register_basic,
            R.id.register_hours,
            R.id.register_drawing,
            R.id.my_page_password
        )

        // 네비게이션으로 이동할 경우 스택 비우기
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            navController.popBackStack(navController.graph.startDestinationId, false)
            navController.navigate(item.itemId)
            true
        }

        // 네비게이션 숨기는 함수
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.visibility =
                if (destination.id in hideNavDestinations) View.GONE else View.VISIBLE
        }
    }

}