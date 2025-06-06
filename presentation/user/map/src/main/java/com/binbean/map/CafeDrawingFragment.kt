package com.binbean.map

import android.R
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import com.binbean.domain.cafe.Cafe
import com.binbean.map.databinding.FragmentCafeDrawingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeDrawingFragment : Fragment() {
    private lateinit var binding: FragmentCafeDrawingBinding
    private val viewModel: CafeDrawingViewModel by viewModels()

    private var cafe: Cafe? = null

    // 갤러리를 여는 함수
    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.detect(requireContext(), uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cafe = arguments?.getSerializable("cafe") as? Cafe
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

        cafe?.let {
            binding.tvCafeName.text = it.name
        }

        val items = listOf("1층", "2층", "3층")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.floorSpinner.adapter = adapter

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnRefresh.setOnClickListener {
            getImage.launch("image/*")
        }
    }
}