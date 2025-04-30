package com.binbean.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        initBottomNavigation()
        if (savedInstanceState == null) {
            switchFragment(AdminHomeFragment())
        }
    }


    /**
     * 하단 네비게이션바 초기화
     */
    private fun initBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigationView
        val indicatorBar = binding.indicatorBar

        // 첫 번째 아이템에 대한 초기 설정
        initIndicatorPosition(bottomNavigationView, indicatorBar)
        setBottomNavigation(bottomNavigationView, indicatorBar)
    }


    /**
     * 하단 네비게이션바 설정
     */
    private fun setBottomNavigation(
        bottomNavigationView: BottomNavigationView,
        indicatorBar: View
    ) {
        bottomNavigationView.run {
            setOnItemSelectedListener {
                updateIndicatorPosition(it, bottomNavigationView, indicatorBar)
                when (it.itemId) {
                    R.id.navi_home -> {
                        switchFragment(AdminHomeFragment())
                        true
                    }

                    R.id.navi_registration -> {
                        switchFragment(AdminRegisterFragment())
                        true
                    }

                    R.id.navi_my -> {
                    }

                    else -> false
                }
                true
            }
        }
    }


    /**
     * 프래그먼트 전환
     */
    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


    /**
     * 첫 번째 아이템의 인디케이터 위치 설정
     */
    private fun initIndicatorPosition(
        bottomNavigationView: BottomNavigationView,
        indicatorBar: View
    ) {
        bottomNavigationView.post {
            val firstItem = bottomNavigationView.findViewById<View>(R.id.navi_home)
            val itemWidth = firstItem.width
            val itemXPosition = firstItem.left

            // indicatorBar의 크기 설정
            val indicatorWidth = (itemWidth * 0.7).toInt()
            val params = indicatorBar.layoutParams
            params.width = indicatorWidth
            indicatorBar.layoutParams = params

            // 첫 번째 아이템의 중앙 위치 계산
            val centerXPosition = calculateCenterPosition(itemXPosition, itemWidth, indicatorWidth)

            // indicatorBar의 위치 설정
            indicatorBar.translationX = centerXPosition.toFloat()
        }
    }


    /**
     * 아이템 선택 시 인디케이터 위치 업데이트
     */
    private fun updateIndicatorPosition(
        item: MenuItem,
        bottomNavigationView: BottomNavigationView,
        indicatorBar: View
    ) {
        val selectedItem = bottomNavigationView.findViewById<View>(item.itemId)
        val location = IntArray(2)
        selectedItem.getLocationOnScreen(location)
        val xPosition = location[0]
        val itemWidth = selectedItem.width

        // 아이템 중앙 위치 계산
        val centerXPosition = calculateCenterPosition(xPosition, itemWidth, indicatorBar.width)

        // indicatorBar의 위치 애니메이션
        indicatorBar.animate().translationX(centerXPosition.toFloat()).setDuration(300).start()
    }


    /**
     * 인디케이터 중앙 위치 계산 함수
     */
    private fun calculateCenterPosition(
        itemXPosition: Int,
        itemWidth: Int,
        indicatorWidth: Int
    ): Int {
        return itemXPosition + itemWidth / 2 - indicatorWidth / 2
    }

}