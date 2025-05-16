package com.binbean.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binbean.register.databinding.FragmentRegisterBasicBinding

class RegisterBasicFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBasicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioButtonControl()

        binding.registerButton.setOnClickListener {
            val action = RegisterBasicFragmentDirections.actionRegistrationToHours()
            findNavController().navigate(action)
        }
    }

    /**
     * 라디오 그룹 제어 함수
     */
    private fun radioButtonControl() {
        setupRadioGroupColors(
            binding.radioWifi, binding.wifiOn.id, binding.iconWifi, binding.textWifi
        )
        setupRadioGroupColors(
            binding.radioCharging, binding.chargingOn.id, binding.iconCharging, binding.textCharging
        )
        setupRadioGroupColors(
            binding.radioKids, binding.kidsOn.id, binding.iconKids, binding.textKids
        )
        setupRadioGroupColors(
            binding.radioPets, binding.petsOn.id, binding.iconPets, binding.textPets
        )
    }

    /**
     * 라디오 그룹 색상 변경 함수
     */
    private fun setupRadioGroupColors(
        radioGroup: RadioGroup,
        activateButtonId: Int,
        iconView: ImageView,
        textView: TextView
    ) {
        val activateIconColor =
            ContextCompat.getColor(requireContext(), com.binbean.resource.R.color.main_green)
        val activateTextColor =
            ContextCompat.getColor(requireContext(), com.binbean.resource.R.color.sub2)
        val deactivatedColor =
            ContextCompat.getColor(requireContext(), com.binbean.resource.R.color.gs_sub)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val isActivated = checkedId == activateButtonId

            val iconColor = if (isActivated) activateIconColor else deactivatedColor
            val textColor = if (isActivated) activateTextColor else deactivatedColor

            iconView.setColorFilter(iconColor)
            textView.setTextColor(textColor)
        }
    }
}