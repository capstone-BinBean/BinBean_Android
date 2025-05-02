package com.binbean.map

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.binbean.map.databinding.FragmentCafeSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeSearchFragment : Fragment() {
    private lateinit var binding: FragmentCafeSearchBinding
    private val viewModel: CafeSearchViewModel by viewModels()

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

        setupEditTextListener()
        observeSearchResult()
    }

    private fun setupEditTextListener() {
        binding.searchEditText.addTextChangedListener {
            val query = it.toString()
            if (query.isNotEmpty()) {
                viewModel.searchCafeKeyword(query)
            }
        }
    }

    private fun observeSearchResult() {
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            Log.d("arieum", result.toString())
        }
    }
}