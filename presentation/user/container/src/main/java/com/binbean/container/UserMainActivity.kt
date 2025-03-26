package com.binbean.container

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.binbean.bookmark.BookMarkFragment
import com.binbean.container.databinding.ActivityUserMainBinding
import com.binbean.map.MapFragment
import com.binbean.mypage.MyInfoFragment

class UserMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationView()

        // 앱 초기 실행 시 홈 화면 설정
        if (savedInstanceState == null) { binding.bottomNavigationView.selectedItemId = R.id.fragment_home }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    val fragment = MapFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.fragment_favorite -> {
                    val fragment = BookMarkFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.fragment_mypage -> {
                    val fragment = MyInfoFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    fun hideNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
}