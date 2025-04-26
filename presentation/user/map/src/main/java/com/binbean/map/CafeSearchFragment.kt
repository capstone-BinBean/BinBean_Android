package com.binbean.map

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binbean.map.databinding.FragmentCafeSearchBinding

class CafeSearchFragment : Fragment() {
    private lateinit var binding: FragmentCafeSearchBinding

    companion object {
        fun newInstance() = CafeSearchFragment()
    }

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
}