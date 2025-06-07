package com.binbean.register

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binbean.admin.dto.CafeRegisterRequest
import com.binbean.register.databinding.FragmentRegisterBasicBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterBasicFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBasicBinding
    private val photoList = mutableListOf<Uri>()
    private lateinit var adapter: PhotoAdapter
    private val viewModel: CafeRegisterViewModel by activityViewModels()
    private lateinit var baseRequest: CafeRegisterRequest

    // 갤러리를 여는 함수
    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                photoList.add(it)
                adapter.notifyItemInserted(photoList.size - 1)
                updatePhotoRcvVisibility()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioButtonControl()
        initPhotoRcv()
        initClickListener()
    }

    /**
     * 기본 정보 전달 힘수
     */
    private fun makeBasicRequest() {
        baseRequest = CafeRegisterRequest(
            cafeName = binding.tradeWindow.text.toString(),
            cafeAddress = binding.addressWindow.text.toString(),
            latitude = 37.0,
            longitude = 127.0,
            cafePhone = binding.phoneWindow.text.toString(),
            wifiAvailable = binding.radioWifi.getSelectedTagValueAsInt(),
            chargerAvailable = binding.radioCharging.getSelectedTagValueAsInt(),
            kidsAvailable = binding.radioKids.getSelectedTagValueAsInt(),
            petAvailable = binding.radioPets.getSelectedTagValueAsInt(),
            cafeDescription = binding.editAddiInfo.text.toString()
        )
    }

    /**
     * 라디오 그륩의 선택된 값의 태그를 읽어오는 함수 (불가능: 0, 가능: 1)
     */
    fun RadioGroup.getSelectedTagValueAsInt(): Int {
        return findViewById<RadioButton>(checkedRadioButtonId).tag.toString().toInt()
    }

    /**
     * 클릭 리스너 초기화 함수
     */
    private fun initClickListener() {
        binding.uploadPhotoWindow.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.registerButton.setOnClickListener {
            makeBasicRequest()
            Log.d(TAG, baseRequest.toString())
            viewModel.setRequest(baseRequest)
            viewModel.setImageUris(photoList)
            val action = RegisterBasicFragmentDirections.actionRegistrationToHours()
            findNavController().navigate(action)
        }
    }

    /**
     * 사진 리사이클러 뷰 초기화 함수
     */
    private fun initPhotoRcv() {
        initPhotoRcvAdapter()
        val recyclerView = binding.photoRcv
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        updatePhotoRcvVisibility()
    }

    /**
     * 사진 리사이클러 뷰 어댑터 설정 함수
     */
    private fun initPhotoRcvAdapter() {
        adapter = PhotoAdapter(
            photoList,
            onClick = { getImage.launch("image/*") },
            onDelete = {
                photoList.removeAt(it)
                adapter.notifyItemRemoved(it)
                updatePhotoRcvVisibility()
            }
        )
    }

    /**
     * 사진 리사이클러뷰 가시성 제어 함수
     */
    private fun updatePhotoRcvVisibility() {
        binding.photoRcv.visibility = if (photoList.isEmpty()) View.GONE else View.VISIBLE
        binding.uploadPhotoWindow.visibility = if (photoList.isEmpty()) View.VISIBLE else View.GONE
    }

    /**
     * 라디오 그룹 제어 함수
     */
    private fun radioButtonControl() {
        setupRadioGroupColors(
            binding.radioWifi, binding.wifiOn.id, binding.iconWifi, binding.textWifi
        )
        setupRadioGroupColors(
            binding.radioCharging, binding.chargingOn.id, binding.iconCharging, binding.textCharging
        )
        setupRadioGroupColors(
            binding.radioKids, binding.kidsOn.id, binding.iconKids, binding.textKids
        )
        setupRadioGroupColors(
            binding.radioPets, binding.petsOn.id, binding.iconPets, binding.textPets
        )
    }

    /**
     * 라디오 그룹 색상 변경 함수
     */
    private fun setupRadioGroupColors(
        radioGroup: RadioGroup,
        activateButtonId: Int,
        iconView: ImageView,
        textView: TextView
    ) {
        val activateIconColor =
            ContextCompat.getColor(requireContext(), com.binbean.resource.R.color.main_green)
        val activateTextColor =
            ContextCompat.getColor(requireContext(), com.binbean.resource.R.color.sub2)
        val deactivatedColor =
            ContextCompat.getColor(requireContext(), com.binbean.resource.R.color.gs_sub)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val isActivated = checkedId == activateButtonId

            val iconColor = if (isActivated) activateIconColor else deactivatedColor
            val textColor = if (isActivated) activateTextColor else deactivatedColor

            iconView.setColorFilter(iconColor)
            textView.setTextColor(textColor)
        }
    }

    companion object {
        private const val TAG = "RegisterBasicFragment"
    }
}