package com.binbean.map

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.FloorPlanResponse
import com.binbean.map.databinding.FragmentCafeDrawingBinding
import com.binbean.ui.CanvasView
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

        binding.canvasView.mode = CanvasView.Mode.USER

        if (cafe != null) {
            binding.tvCafeName.text = cafe?.name
        } else if (cafeId != null) {
            viewModel.loadCafeDetail(cafeId!!)
            observeServerCafeData()
            observeServerFloorPlanData()
        }
    }

    private fun observeServerCafeData() {
        viewModel.cafeDetail.observe(viewLifecycleOwner) { cafeDetail ->
            binding.tvCafeName.text = cafeDetail.cafeName
            cafeDetail.floorPlanId.firstOrNull()?.id?.let { planId ->
                viewModel.loadFloorPlan(planId)
            }
        }
    }

    private fun observeServerFloorPlanData() {
        viewModel.floorPlans.observe(viewLifecycleOwner) { plans ->
            val firstFloor = plans.firstOrNull { it.floorNumber == 1 }
            firstFloor?.let {
                val floorList = it.floorList
                val currentSeats = it.currentSeats.currentPosition
                binding.canvasView.renderFloorPlan(floorList, currentSeats)
            }

            setupFloorSpinner(plans)
        }
    }

    private fun setupFloorSpinner(floorPlans: List<FloorPlanResponse>) {
        val floorNumbers = floorPlans.map { it.floorNumber }.sorted()
        val items = floorNumbers.map { "${it}층" }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.floorSpinner.adapter = adapter

        setupFloorSpinnerListener(floorPlans, floorNumbers)
    }

    private fun setupFloorSpinnerListener(
        floorPlans: List<FloorPlanResponse>,
        floorNumbers: List<Int>
    ) {
        binding.floorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedFloor = floorNumbers[position]
                val selectedPlan = floorPlans.firstOrNull { it.floorNumber == selectedFloor }

                selectedPlan?.let {
                    binding.canvasView.renderFloorPlan(
                        it.floorList,
                        it.currentSeats.currentPosition
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}