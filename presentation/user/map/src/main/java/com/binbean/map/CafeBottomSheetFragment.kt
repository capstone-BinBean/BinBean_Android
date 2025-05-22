package com.binbean.map

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.CafeDetail
import com.binbean.domain.cafe.CafeInfoImgItem
import com.binbean.domain.cafe.toCafe
import com.binbean.map.adapter.CafeInfoImgAdapter
import com.binbean.map.adapter.ViewPagerAdapter
import com.binbean.map.databinding.FragmentCafeBottomSheetBinding
import com.binbean.map.viewmodel.CafeBottomSheetViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val DEFAULT_PEEK_HEIGHT = 650
        private val ARG_CAFE = "cafe"
        private const val ARG_CAFE_ID = "cafeId"

        @JvmStatic
        fun newInstance(cafe: Cafe): CafeBottomSheetFragment {
            return CafeBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CAFE, cafe)
                }
            }
        }

        @JvmStatic
        fun newInstance(cafeId: Int): CafeBottomSheetFragment {
            return CafeBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CAFE_ID, cafeId)
                }
            }
        }

    }

    private var _binding: FragmentCafeBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var adapter: CafeInfoImgAdapter

    private val viewModel: CafeBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCafeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cafe: Cafe? = arguments?.getSerializable(ARG_CAFE) as? Cafe
        val cafeId: Int? = arguments?.getInt(ARG_CAFE_ID)

        if (cafe != null) {
            // 카카오 API 마커에서 클릭한 경우
            viewModel.setCafe(cafe)
            observeKakaoCafeData()
            setupViewPager(cafe)
        } else if (cafeId != null && cafeId > 0) {
            // 서버 API 마커에서 클릭한 경우
            viewModel.loadCafeDetail(cafeId)
            observeServerCafeData()
        }

        initAdapter()
        observeCafeInfoImg()
        viewModel.loadCafeInfoImg()
        binding.btnSeatCheck.setOnClickListener {
            val fragment = CafeDrawingFragment().apply {
                arguments = Bundle().apply {
                    if (cafeId != null && cafeId > 0) {
                        putInt(ARG_CAFE_ID, cafeId)
                    } else if (cafe != null) {
                        putSerializable(ARG_CAFE, cafe)
                    }
                }
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()

            dismiss() // BottomSheet는 닫아줘야 함
        }

        binding.btnBack.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { setupBottomSheet(it) }
        }

        return dialog
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

    private fun observeKakaoCafeData() {
        viewModel.cafe.observe(viewLifecycleOwner) { cafe ->
            cafe?.let {
                binding.tvStoreName.text = it.name
                binding.tvAddress.text = it.address
                binding.tvPhoneNumber.text = it.phone
            }
        }
    }

    private fun observeServerCafeData() {
        viewModel.cafeDetail.observe(viewLifecycleOwner) { detail ->
            detail?.let {
                binding.tvStoreName.text = it.cafeName
                binding.tvAddress.text = it.cafeAddress
                binding.tvPhoneNumber.text = it.cafePhone

                adapter.submitList(it.cafeImgUrl.map { img -> CafeInfoImgItem(img.url) })

                setupViewPager(detail)
            }
        }
    }


    private fun setupViewPager(cafe: Cafe) {
        val adapter = ViewPagerAdapter(this, cafe = cafe, cafeDetail = null)
        binding.viewPager.adapter = adapter
        attachTabs()
    }

    private fun setupViewPager(detail: CafeDetail) {
        val adapter = ViewPagerAdapter(this, cafe = null, cafeDetail = detail)
        binding.viewPager.adapter = adapter
        attachTabs()
    }

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

    private fun setupBottomSheet(bottomSheet: View) {
        configureBottomSheetLayout(bottomSheet)
        initBottomSheetBehavior(bottomSheet)
        applyInitialUiState()
        attachBottomSheetCallback(bottomSheet)
    }

    private fun configureBottomSheetLayout(bottom: View) {
        bottom.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottom.requestLayout()
    }

    private fun initBottomSheetBehavior(bottom: View) {
        behavior = BottomSheetBehavior.from(bottom).apply {
            peekHeight = DEFAULT_PEEK_HEIGHT
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun applyInitialUiState() {
        binding.indicator.visibility = View.VISIBLE
        binding.btnBack.visibility = View.GONE
        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.setBackgroundResource(R.drawable.bottom_sheet_bg)
    }

    private fun attachBottomSheetCallback(bottom: View) {
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> applyExpandedState(bottomSheet)
                    BottomSheetBehavior.STATE_COLLAPSED -> applyCollapsedState(bottomSheet)
                    BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun applyExpandedState(bottom: View) {
        binding.indicator.visibility = View.GONE
        binding.btnBack.visibility = View.VISIBLE

        bottom.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottom.requestLayout()
    }

    private fun applyCollapsedState(bottom: View) {
        binding.indicator.visibility = View.VISIBLE
        binding.btnBack.visibility = View.GONE

        bottom.setBackgroundResource(R.drawable.bottom_sheet_bg)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}