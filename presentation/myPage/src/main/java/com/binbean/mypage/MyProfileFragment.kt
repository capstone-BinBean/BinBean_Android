package com.binbean.mypage

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binbean.mypage.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {
    private lateinit var binding: FragmentMyProfileBinding

    // 갤러리를 여는 함수
    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.profile.setPadding(0)
                binding.profile.setImageURI(it)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListeners()
    }

    /**
     * 클릭 리스너 초기화 함수
     */
    private fun initClickListeners() {
        binding.profileWindow.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.review.setOnClickListener {
            val action = MyProfileFragmentDirections.actionMypageToReview()
            findNavController().navigate(action)
        }

        binding.changePassword.setOnClickListener {
            val action = MyProfileFragmentDirections.actionMypageToPassword()
            findNavController().navigate(action)
        }
    }
}