package com.binbean.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.binbean.bookmark.databinding.FragmentBookMarkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookMarkFragment : Fragment() {
    private lateinit var binding: FragmentBookMarkBinding
    private val viewModel: BookMarkViewModel by viewModels()

    private lateinit var adapter: BookmarkedCafeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookMarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCafeItemRecyclerView()
        observeBookmarkedCafes()
    }

    private fun setupCafeItemRecyclerView() {
        adapter = BookmarkedCafeListAdapter()
        binding.rvCafeList.adapter = adapter
    }

    private fun observeBookmarkedCafes() {
        viewModel.bookmarkedCafes.observe(viewLifecycleOwner) { cafeList ->
            adapter.submitList(cafeList)
        }
    }
}