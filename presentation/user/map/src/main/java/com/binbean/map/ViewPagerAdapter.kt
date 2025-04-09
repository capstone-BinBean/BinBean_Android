package com.binbean.map

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arieum.review.CafeReviewFragment
import com.binbean.domain.cafe.Cafe

class ViewPagerAdapter(
    fragment: Fragment,
    private val cafe: Cafe
): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CafeDetailedInfoFragment.newInstance(cafe)
            1 -> CafeReviewFragment.newInstance(cafe)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}