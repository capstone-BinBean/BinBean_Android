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
import com.binbean.map.viewmodel.MapViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class MapFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModels()

    private lateinit var defaultLabelStyle: LabelStyle
    private lateinit var selectedLabelStyle: LabelStyle
    private lateinit var currentLocationStyle: LabelStyle
    private var lastSelectedLabel: Label? = null

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
                initMarkerStyles(p0)
                setupLabelClickListener(p0)
                setupCameraMoveEndListener(p0)
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
                addCurrentLocationMarker(map, currentLatLng)
                viewModel.loadCafes(location.latitude, location.longitude)
                observeCafes(map)
            } else {
                Log.e("kakaoMap", "현재 위치 정보 없음")
            }
        }
    }

    private fun setupCameraMoveEndListener(map: KakaoMap) {
        map.setOnCameraMoveEndListener { _, cameraPosition, _ ->
            val center = cameraPosition.position
            viewModel.loadCafes(center.latitude, center.longitude)
        }
    }

    private fun observeCafes(map: KakaoMap){
        viewModel.cafeList.observe(viewLifecycleOwner) { cafes ->
            removeAllCafeMarkers(map)
            addCafeMarker(map, cafes)
        }
    }

    private fun initMarkerStyles(map: KakaoMap) {
        val myBlack = context?.let { ContextCompat.getColor(it, com.binbean.ui.R.color.black) }
        val myWhite = context?.let { ContextCompat.getColor(it, com.binbean.ui.R.color.white) }

        defaultLabelStyle = LabelStyle.from(R.drawable.marker_unregistered)
            .setTextStyles(20, myBlack!!, 2, myWhite!!)

        selectedLabelStyle = LabelStyle.from(R.drawable.marker_unregistered_focused)
            .setTextStyles(20, myBlack, 2, myWhite)

        currentLocationStyle = LabelStyle.from(R.drawable.marker_user_location)

        val labelStylesLst = listOf(
            defaultLabelStyle,
            selectedLabelStyle,
            currentLocationStyle
        )

        val labelStyles = LabelStyles.from("multiStyle", labelStylesLst)
        map.labelManager?.addLabelStyles(labelStyles)
    }

    private fun addCurrentLocationMarker(map: KakaoMap, latLng: LatLng) {
        val labelLayer = map.labelManager?.layer

        val label = createLocationLabel(latLng)
        labelLayer?.addLabel(label)
    }

    private fun createLocationLabel(latLng: LatLng): LabelOptions {
        return LabelOptions.from(latLng)
            .setStyles(currentLocationStyle)
            .setTag(CURRENT_LOCATION_MARKER_TAG)
    }

    private fun addCafeMarker(map: KakaoMap, cafes: List<Cafe>) {
        val labelLayer = map.labelManager?.layer
        // labelLayer?.removeAll()

        cafes.forEach { cafe ->
            val label = createCafeLabel(cafe)
            labelLayer?.addLabel(label)
        }
    }

    private fun createCafeLabel(cafe: Cafe): LabelOptions {
        val position = LatLng.from(cafe.latitude, cafe.longitude)
        return LabelOptions.from(position)
            .setTexts(LabelTextBuilder().setTexts(cafe.name))
            .setStyles(defaultLabelStyle)
            .setTag(cafe)
    }

    private fun setupLabelClickListener(map: KakaoMap) {
        map.setOnLabelClickListener { _, _, label ->
            val clickedCafe = label.tag as? Cafe
            clickedCafe?.let { viewModel.selectCafe(it) }

            // 이전 선택 라벨 원상복귀
            lastSelectedLabel?.setStyles(defaultLabelStyle)

            // 현재 선택된 라벨 스타일 변경
            label.setStyles(selectedLabelStyle)
            label.show(true, 0)
            lastSelectedLabel = label

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

    private fun removeAllCafeMarkers(map: KakaoMap) {
        val labelLayer = map.labelManager?.layer ?: return
        val labels = labelLayer.allLabels.toList()
        labels.forEach { label ->
            if (label.tag is Cafe) {
                labelLayer.remove(label)
            }
        }
    }

    companion object {
        private const val CURRENT_LOCATION_MARKER_TAG = "currentLocation"

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