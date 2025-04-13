package com.binbean.map

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binbean.domain.cafe.Cafe
import com.binbean.map.databinding.FragmentCafeDetailedInfoBinding
import com.binbean.map.viewmodel.CafeDetailedInfoViewModel

class CafeDetailedInfoFragment : Fragment() {

    private var _binding: FragmentCafeDetailedInfoBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(cafe: Cafe): CafeDetailedInfoFragment {
            val fragment = CafeDetailedInfoFragment()
            val bundle = Bundle().apply {
                putSerializable("cafe", cafe)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: CafeDetailedInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCafeDetailedInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}