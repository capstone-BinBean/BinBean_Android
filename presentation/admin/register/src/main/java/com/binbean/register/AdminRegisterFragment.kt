package com.binbean.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binbean.register.databinding.FragmentAdminRegisterBinding

class AdminRegisterFragment : Fragment() {
    private lateinit var binding: FragmentAdminRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val action = AdminRegisterFragmentDirections.actionRegistrationToBasic()
            findNavController().navigate(action)
        }
    }

}