package com.binbean.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binbean.mypage.databinding.FragmentChangePwBinding

class ChangePwFragment : Fragment() {
    private lateinit var binding: FragmentChangePwBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            val action = ChangePwFragmentDirections.actionMypageToProfile()
            findNavController().navigate(action)
        }

        binding.completeButton.setOnClickListener {
            val action = ChangePwFragmentDirections.actionMypageToProfile()
            findNavController().navigate(action)
        }
    }
}