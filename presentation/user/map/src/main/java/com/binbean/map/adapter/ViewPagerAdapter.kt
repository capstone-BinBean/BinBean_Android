package com.binbean.map.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.binbean.review.CafeReviewFragment
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.CafeDetail
import com.binbean.map.CafeDetailedInfoFragment

class ViewPagerAdapter(
    fragment: Fragment,
    private val cafe: Cafe?,
    private val cafeDetail: CafeDetail?
): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                if (cafeDetail != null) {
                    CafeDetailedInfoFragment.newInstance(cafeDetail)
                } else if (cafe != null) {
                    CafeDetailedInfoFragment.newInstance(cafe)
                } else throw IllegalStateException("No data provided")
            }
            1 -> {
                if (cafeDetail != null) {
                    CafeReviewFragment.newInstance(cafeDetail)
                } else if (cafe != null) {
                    CafeReviewFragment.newInstance(cafe)
                } else throw IllegalStateException("No data provided")
            }
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}