package com.binbean.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.binbean.domain.cafe.Cafe
import com.binbean.map.databinding.FragmentMapBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModels()

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineGranted || coarseGranted) { setupMapView() }
            else { Toast.makeText(requireContext(), "위치 권한이 필요합니다", Toast.LENGTH_SHORT).show() }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLocationPermission()
        observeSelectedCafe()
    }

    private fun checkLocationPermission(){
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun setupMapView(){
        binding.mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                Log.d("kakaoMap", "카카오맵 정상종료")
            }
            override fun onMapError(p0: Exception?) {
                p0.let { Log.e("kakaoMap", p0.toString()) }
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(p0: KakaoMap) {
                Log.d("kakaoMap", "카카오맵 정상실행")
                moveMapToCurrentLocation(p0)
                setupLabelClickListener(p0)
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun moveMapToCurrentLocation(map: KakaoMap){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener {
            val location = it
            if (location != null) {
                val currentLatLng = LatLng.from(location.latitude, location.longitude)
                map.moveCamera(CameraUpdateFactory.newCenterPosition(currentLatLng))
                Log.d("kakaoMap", "현재 위치로 이동: ${location.latitude}, ${location.longitude}")
                viewModel.loadCafes(location.latitude, location.longitude)
                observeCafes(map)
            } else {
                Log.e("kakaoMap", "현재 위치 정보 없음")
            }
        }
    }

    private fun observeCafes(map: KakaoMap){
        viewModel.cafeList.observe(viewLifecycleOwner) { cafes ->
            addCafeMarker(map, cafes)
        }
    }

    // 카페 마커 추가 함수
    private fun addCafeMarker(map: KakaoMap, cafes: List<Cafe>) {
        val myBlack = context?.let { ContextCompat.getColor(it, com.binbean.ui.R.color.black) }
        val myWhite = context?.let { ContextCompat.getColor(it, com.binbean.ui.R.color.white) }

        val labelManager = map.labelManager
        val labelLayer = labelManager?.layer

        // labelLayer?.removeAll()

        // 라벨 스타일 정의 (이미지를 마커처럼 사용)
        val labelStyles = LabelStyles.from(
            "cafeStyle",
            LabelStyle.from(R.drawable.marker_unregistered) // 마커 이미지
                .setTextStyles(20, myBlack!!, 2, myWhite!!) // 텍스트 스타일
        )
        // 스타일 등록 (한 번만 하면 좋지만, 여기선 간단히 포함)
        labelManager?.addLabelStyles(labelStyles)

        cafes.forEach { cafe ->
            val label = createCafeLabel(cafe, labelStyles)
            labelLayer?.addLabel(label)
        }

    }

    private fun createCafeLabel(cafe: Cafe, labelStyles: LabelStyles): LabelOptions {
        val position = LatLng.from(cafe.latitude, cafe.longitude)
        return LabelOptions.from(position)
            .setTexts(LabelTextBuilder().setTexts(cafe.name))
            .setStyles(labelStyles)
            .setTag(cafe)
    }

    private fun setupLabelClickListener(map: KakaoMap) {
        map.setOnLabelClickListener { kakaoMap, _, label ->
            val clickedCafe = label.tag as? Cafe
            clickedCafe?.let {
                viewModel.selectCafe(it)
            }
            true
        }
    }

    private fun showCafeBottomSheet(cafe: Cafe) {
        val bottomSheet = CafeBottomSheetFragment.newInstance(cafe)
        bottomSheet.show(parentFragmentManager, "CafeBottomSheetFragment")
    }

    private fun observeSelectedCafe() {
        viewModel.selectedCafe.observe(viewLifecycleOwner) { cafe ->
            cafe?.let {
                showCafeBottomSheet(it)
                viewModel.clearSelectedCafe()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.pause()
    }
}