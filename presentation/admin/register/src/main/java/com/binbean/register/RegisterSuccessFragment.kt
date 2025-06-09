package com.binbean.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binbean.domain.cafe.CafeDetail
import com.binbean.domain.cafe.CafeInfoImgItem
import com.binbean.map.adapter.CafeInfoImgAdapter
import com.binbean.map.adapter.ViewPagerAdapter
import com.binbean.register.databinding.FragmentRegisterSuccessBinding
import com.google.android.material.tabs.TabLayoutMediator

class RegisterSuccessFragment : Fragment() {
    private lateinit var binding: FragmentRegisterSuccessBinding
    private val viewModel: CafeSuccessViewModel by activityViewModels()
    private lateinit var adapter: CafeInfoImgAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cafeId = arguments?.getInt("cafeId", -1) ?: -1
        Log.d("RegisterSuccessFragment", "받은 cafeId: $cafeId")

        initAdapter()
        observeCafeInfoImg()
        observeServerCafeData()
        viewModel.loadCafeDetail(cafeId)
    }

    private fun initAdapter() {
        adapter = CafeInfoImgAdapter()
        binding.rvCafeImage.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCafeImage.adapter = adapter
    }

    private fun observeCafeInfoImg() {
        viewModel.photoList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun observeServerCafeData() {
        viewModel.cafeDetail.observe(viewLifecycleOwner) { detail ->
            detail?.let {
                binding.tvStoreName.text = it.cafeName
                binding.tvAddress.text = it.cafeAddress
                binding.tvPhoneNumber.text = it.cafePhone
                binding.tvScore.text = String.format("%.1f", it.reviewAvg)

                adapter.submitList(it.cafeImgUrl.map { img -> CafeInfoImgItem(img.url) })

                setupViewPager(detail)
            }
        }
    }

    /**
     * 뷰페이저 설정 함수
     */
    private fun setupViewPager(detail: CafeDetail) {
        val adapter = ViewPagerAdapter(this, cafe = null, cafeDetail = detail)
        binding.viewPager.adapter = adapter
        attachTabs()
    }

    /**
     * 탭 레이아웃 설정 함수
     */
    private fun attachTabs() {
        binding.viewPager.isNestedScrollingEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "상세정보"
                1 -> "리뷰"
                else -> ""
            }
        }.attach()
    }
}