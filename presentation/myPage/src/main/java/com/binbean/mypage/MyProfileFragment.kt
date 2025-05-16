package com.binbean.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binbean.mypage.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {
    private lateinit var binding: FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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