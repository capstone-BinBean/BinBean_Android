package com.binbean.register

import android.R
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        setHours()

        binding.registerButton.setOnClickListener {
            val action = RegisterHoursFragmentDirections.actionRegistrationToDrawing()
            findNavController().navigate(action)
        }
    }

    private fun setHours() {
        binding.monStart.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val timeString = "%02d:%02d".format(hourOfDay, minute)
        }

        TimePickerDialog(requireContext(), listener, hour, minute, true).show()
    }

}