package com.binbean.map

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.binbean.map.adapter.CafeSearchAdapter
import com.binbean.map.databinding.FragmentCafeSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeSearchFragment : Fragment() {
    private lateinit var binding: FragmentCafeSearchBinding
    private val viewModel: CafeSearchViewModel by viewModels()
    private val cafeAdapter = CafeSearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupEditTextListener()
        observeSearchResult()
    }

    private fun setupRecyclerView() {
        binding.searchResultRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cafeAdapter
        }
    }

    private fun setupEditTextListener() {
        binding.searchEditText.addTextChangedListener {
            val query = it.toString()
            if (query.isNotEmpty()) {
                viewModel.searchCafeKeyword(query)
            } else {
                binding.searchResultRecycler.isVisible = false
                binding.emptyText.isInvisible = false
                cafeAdapter.submitList(emptyList())
            }
        }
    }

    private fun observeSearchResult() {
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            Log.d("CafeSearchFragment_EditTextView", result.toString())
            val hasResult = result.isNotEmpty()
            binding.searchResultRecycler.isVisible = hasResult
            binding.emptyText.isVisible = !hasResult
            cafeAdapter.submitList(result)
        }
    }
}