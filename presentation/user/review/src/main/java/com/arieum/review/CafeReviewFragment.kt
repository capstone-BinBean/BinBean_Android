package com.arieum.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.arieum.review.databinding.FragmentCafeReviewBinding
import com.binbean.domain.cafe.Cafe

class CafeReviewFragment : Fragment() {

    private var _binding: FragmentCafeReviewBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(cafe: Cafe): CafeReviewFragment {
            val fragment = CafeReviewFragment()
            val bundle = Bundle().apply {
                putSerializable("cafe", cafe)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: CafeReviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCafeReviewBinding.inflate(inflater, container, false)
        return binding.root
    }
}