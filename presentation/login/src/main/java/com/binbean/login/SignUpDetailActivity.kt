package com.binbean.login

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.binbean.login.databinding.ActivitySignUpDetailBinding

class SignUpDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpDetailBinding

    // 갤러리를 여는 함수
    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.profile.setPadding(0)
                binding.profile.setImageURI(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListeners()
    }

    /**
     * 클릭 리스너 초기화 함수
     */
    private fun initClickListeners() {
        binding.profileWindow.setOnClickListener {
            getImage.launch("image/*")
        }
        binding.photoInstruction.setOnClickListener {
            getImage.launch("image/*")
        }
    }
}