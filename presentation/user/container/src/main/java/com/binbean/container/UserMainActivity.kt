package com.binbean.container

import android.os.Bundle
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

    fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    val fragment = MapFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragment_favorite -> {
                    val fragment = BookMarkFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragment_mypage -> {
                    val fragment = MyInfoFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
}