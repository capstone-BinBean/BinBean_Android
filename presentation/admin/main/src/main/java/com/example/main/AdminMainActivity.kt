package com.example.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.main.databinding.ActivityAdminMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setIndicatorBar()
    }


    private fun setIndicatorBar() {
        val bottomNavigationView = binding.bottomNavigationView
        val indicatorBar = binding.indicatorBar

        // 첫 번째 아이템에 대한 초기 설정
        initialIndicatorPosition(bottomNavigationView, indicatorBar)

        // 아이템 선택 시 위치 업데이트
        bottomNavigationView.setOnItemSelectedListener { item ->
            updateIndicatorPosition(item, bottomNavigationView, indicatorBar)
            true
        }
    }


    /**
     * 첫 번째 아이템의 인디케이터 위치 설정
     */
    private fun initialIndicatorPosition(
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