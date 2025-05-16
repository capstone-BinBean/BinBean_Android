package com.binbean.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.binbean.register.databinding.FragmentRegisterHoursBinding

class RegisterHoursFragment : Fragment() {
    private lateinit var binding: FragmentRegisterHoursBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val action = RegisterHoursFragmentDirections.actionRegistrationToDrawing()
            findNavController().navigate(action)
        }
    }
}