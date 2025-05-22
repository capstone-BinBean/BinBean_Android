package com.binbean.map

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.binbean.domain.cafe.Cafe
import com.binbean.map.databinding.FragmentCafeDrawingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeDrawingFragment : Fragment() {
    private lateinit var binding: FragmentCafeDrawingBinding
    private val viewModel: CafeDrawingViewModel by viewModels()

    private var cafe: Cafe? = null
    private var cafeId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cafe = arguments?.getSerializable("cafe") as? Cafe
        cafeId = arguments?.getInt("cafeId", -1)?.takeIf { it > 0 }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeDrawingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (cafe != null) {
            binding.tvCafeName.text = cafe?.name
        } else if (cafeId != null) {
            viewModel.loadCafeDetail(cafeId!!)
            observeServerCafeData()
        }

        setupFloorSpinner()
    }

    private fun observeServerCafeData() {
        viewModel.cafeDetail.observe(viewLifecycleOwner) { cafeDetail ->
            binding.tvCafeName.text = cafeDetail.cafeName
            // + 층별 도면 추가 처리
        }
    }

    private fun setupFloorSpinner() {
        val items = listOf("1층", "2층", "3층")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.floorSpinner.adapter = adapter
    }
}