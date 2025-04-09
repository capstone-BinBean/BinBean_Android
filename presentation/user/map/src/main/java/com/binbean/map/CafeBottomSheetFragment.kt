package com.binbean.map

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binbean.domain.cafe.Cafe
import com.binbean.map.databinding.FragmentCafeBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CafeBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val DEFAULT_PEEK_HEIGHT = 650

        @JvmStatic
        fun newInstance(cafe: Cafe): CafeBottomSheetFragment {
            val fragment = CafeBottomSheetFragment()
            val bundle = Bundle().apply {
                putSerializable("cafe", cafe)
            }
            fragment.arguments = bundle
            return fragment
        }

    }

    private var _binding: FragmentCafeBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var behavior: BottomSheetBehavior<View>

    private val viewModel: CafeBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCafeBottomSheetBinding.inflate(inflater, container, false)
        setupViewPager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cafe = arguments?.getSerializable("cafe") as? Cafe
        cafe?.let {viewModel.setCafe(it)}

        observeCafeData()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { setupBottomSheet(it) }
        }

        return dialog
    }

    private fun observeCafeData() {
        viewModel.cafe.observe(viewLifecycleOwner) { cafe ->
            cafe?.let {
                binding.tvStoreName.text = it.name
                binding.tvAddress.text = it.address
                binding.tvPhoneNumber.text = it.phone
            }
        }
    }

    private fun setupViewPager() {

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