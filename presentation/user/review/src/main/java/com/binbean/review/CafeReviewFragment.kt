package com.binbean.review

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arieum.review.R
import com.arieum.review.databinding.FragmentCafeReviewBinding
import com.binbean.domain.cafe.Cafe

class CafeReviewFragment : Fragment() {

    private var _binding: FragmentCafeReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var starViews: List<AppCompatButton>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CafeReviewAdapter()
        binding.rvReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReviews.adapter = adapter

        viewModel.reviewList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        // 예시용 더미 리뷰 호출
        viewModel.loadDummyReviews()

        starViews = listOf(
            binding.star1, binding.star2, binding.star3, binding.star4, binding.star5
        )

        starViews.forEachIndexed { index, starView ->
            starView.setOnClickListener {
                val selectedRating = index + 1
                updateStarRating(selectedRating)

                // 300ms 후 CafeReviewWriteFragment로 이동
                Handler(Looper.getMainLooper()).postDelayed({
                    val cafe = arguments?.getSerializable("cafe") as? Cafe ?: return@postDelayed
                    val dialog = CafeReviewWriteFragment.newInstance(cafe, selectedRating)
                    dialog.show(childFragmentManager, "ReviewWriteDialog")
                }, 300)
            }
        }
    }

    private fun updateStarRating(rating: Int) {
        for (i in starViews.indices) {
            starViews[i].setBackgroundResource(
                if (i < rating) R.drawable.ic_filled_star else R.drawable.ic_empty_star
            )
        }
    }
}