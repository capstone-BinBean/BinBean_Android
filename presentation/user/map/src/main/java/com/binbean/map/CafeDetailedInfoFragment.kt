package com.binbean.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.CafeDetail
import com.binbean.map.databinding.FragmentCafeDetailedInfoBinding
import com.binbean.map.viewmodel.CafeDetailedInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeDetailedInfoFragment : Fragment() {

    private var _binding: FragmentCafeDetailedInfoBinding? = null
    private val binding get() = _binding!!

    private var cafe: Cafe? = null
    private var cafeDetail: CafeDetail? = null

    companion object {
        fun newInstance(cafe: Cafe): CafeDetailedInfoFragment {
            return CafeDetailedInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("cafe", cafe)
                }
            }
        }

        fun newInstance(detail: CafeDetail): CafeDetailedInfoFragment {
            return CafeDetailedInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("cafeDetail", detail)
                }
            }
        }
    }

    private val viewModel: CafeDetailedInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cafe = arguments?.getSerializable("cafe") as? Cafe
        cafeDetail = arguments?.getSerializable("cafeDetail") as? CafeDetail
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCafeDetailedInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when {
            cafeDetail != null -> setServerCafeData(cafeDetail!!)
            cafe != null -> setKakaoCafeData(cafe!!)
            else -> {
                Toast.makeText(requireContext(), "카페 정보를 불러올 수 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setServerCafeData(cafeDetail: CafeDetail) {
        setIconAndText(binding.tvWifi, binding.ivWifi, cafeDetail.wifiAvailable, R.drawable.ic_focused_wifi, R.drawable.ic_unfocused_wifi)
        setIconAndText(binding.tvCharging, binding.ivCharging, cafeDetail.chargerAvailable, R.drawable.ic_focused_charge, R.drawable.ic_unfocused_charge)
        setIconAndText(binding.tvPet, binding.ivPet, cafeDetail.petAvailable, R.drawable.ic_focused_animal, R.drawable.ic_unfocused_animal)
        setIconAndText(binding.tvEmoji, binding.ivEmoji, cafeDetail.kidsAvailable, R.drawable.ic_focused_kid, R.drawable.ic_unfocused_kid)
        // 관리자 메시지
        binding.tvMessage.text = cafeDetail.cafeDescription ?: "설명이 없습니다"
    }

    private fun setKakaoCafeData(cafe: Cafe) {
        setIconAndText(binding.tvWifi, binding.ivWifi, 1, R.drawable.ic_focused_wifi, R.drawable.ic_unfocused_wifi)
        setIconAndText(binding.tvCharging, binding.ivCharging, 1, R.drawable.ic_focused_charge, R.drawable.ic_unfocused_charge)
        setIconAndText(binding.tvPet, binding.ivPet, 1, R.drawable.ic_focused_animal, R.drawable.ic_unfocused_animal)
        setIconAndText(binding.tvEmoji, binding.ivEmoji, 1, R.drawable.ic_focused_kid, R.drawable.ic_unfocused_kid)
        // 관리자 메시지
        binding.tvMessage.text = cafe.cafeDescription ?: "설명이 없습니다"
    }

    private fun setIconAndText(view: TextView, icon: ImageView, available: Int?, focusedRes: Int, unfocusedRes: Int) {
        val color = if (available == 1) com.binbean.resource.R.color.sub2 else com.binbean.resource.R.color.gs_sub
        view.setTextColor(ContextCompat.getColor(requireContext(), color))
        icon.setImageResource(if (available == 1) focusedRes else unfocusedRes)
    }
}