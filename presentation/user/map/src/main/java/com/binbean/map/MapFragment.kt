package com.binbean.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.ServerCafe
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

@AndroidEntryPoint
class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModels()

    private lateinit var defaultLabelStyle: LabelStyle
    private lateinit var selectedLabelStyle: LabelStyle
    private lateinit var currentLocationStyle: LabelStyle
    private lateinit var serverLabelStyle: LabelStyle
    private var lastSelectedLabel: Label? = null

    /**
     * 위치 권한 요청
     */
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineGranted || coarseGranted) { setupMapView() }
            else { Toast.makeText(requireContext(), "위치 권한이 필요합니다", Toast.LENGTH_SHORT).show() }
        }

    private val cafeSearchFragment = CafeSearchFragment()

    private var lastRequestedLatLng: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        parentFragmentManager.setFragmentResultListener(
            "cafe_marker_request", viewLifecycleOwner
        ) { _, bundle ->
            val name = bundle.getString("cafeName") ?: return@setFragmentResultListener
            val lat = bundle.getDouble("latitude")
            val lng = bundle.getDouble("longitude")

            // showMarkerOnMap(name, lat, lng)
        }
        
        checkLocationPermission() // 위치 권한 요청
        observeSelectedCafe() // 카페 선택 옵저빙
        observeSelectedServerCafe()
        binding.searchView.setOnClickListener {  // 카페 검색 클릭 리스너
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.root, cafeSearchFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    /**
     * 위치 권한 요청
     */
    private fun checkLocationPermission(){
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    /**
     * 카카오맵 뷰 설정
     */
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
                moveMapToCurrentLocation(p0)    // 현재 위치로 이동
                initMarkerStyles(p0)            // 마커 스타일 초기화
                setupLabelClickListener(p0)     // 마커 클릭 리스너 설정
                setupCameraMoveEndListener(p0)  // 카메라 이동 리스너 설정
                observeCafes(p0)                // 카카오 API 카페 리스트 옵저빙
                observeServerCafes(p0)          // 서버 API 카페 리스트 옵저빙
            }
        })
    }

    /**
     * 현재 위치로 이동
     */
    @SuppressLint("MissingPermission")
    private fun moveMapToCurrentLocation(map: KakaoMap){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener {
            val location = it
            if (location != null) {
                val currentLatLng = LatLng.from(location.latitude, location.longitude)

                map.moveCamera(CameraUpdateFactory.newCenterPosition(currentLatLng))
                Log.d("kakaoMap", "현재 위치로 이동: ${location.latitude}, ${location.longitude}")

                addCurrentLocationMarker(map, currentLatLng)                        // 현재 위치 마커 추가
                // viewModel.loadCafes(location.latitude, location.longitude)       // 카카오 API 카페 리스트 요청
                viewModel.loadServerCafes(location.latitude, location.longitude)    // 서버 API 카페 리스트 요청
            } else {
                Log.e("kakaoMap", "현재 위치 정보 없음")
            }
        }
    }

    /**
     * 카메라 이동 리스너 설정
     */
    private fun setupCameraMoveEndListener(map: KakaoMap) {
        map.setOnCameraMoveEndListener { _, cameraPosition, _ ->
            val center = cameraPosition.position

            // 동일 좌표 중복 요청 방지
            if (lastRequestedLatLng != null &&
                lastRequestedLatLng!!.latitude == center.latitude &&
                lastRequestedLatLng!!.longitude == center.longitude) return@setOnCameraMoveEndListener

            lastRequestedLatLng = center

            // viewModel.loadCafes(center.latitude, center.longitude)       // 카카오 API 카페 리스트 요청
            viewModel.loadServerCafes(center.latitude, center.longitude)    // 서버 API 카페 리스트 요청
        }
    }

    /**
     * 카카오 API 카페 리스트 옵저빙
     */
    private fun observeCafes(map: KakaoMap){
        viewModel.cafeList.observe(viewLifecycleOwner) { cafes ->
            map.let { map ->
                removeAllCafeMarkers(map)
                addCafeMarker(map, cafes)
            }
        }
    }

    /**
     * 서버 API 카페 리스트 옵저빙
     */
    private fun observeServerCafes(map: KakaoMap) {
        viewModel.serverCafeList.observe(viewLifecycleOwner) { serverCafes ->
            map.let {
                removeAllServerCafeMarkers(it)
                addServerCafeMarker(it, serverCafes)
            }
        }
    }

    /**
     * 마커 스타일 초기화
     */
    private fun initMarkerStyles(map: KakaoMap) {
        val myBlack = context?.let { ContextCompat.getColor(it, com.binbean.ui.R.color.black) }
        val myWhite = context?.let { ContextCompat.getColor(it, com.binbean.ui.R.color.white) }

        defaultLabelStyle = LabelStyle.from(R.drawable.marker_unregistered)
            .setTextStyles(20, myBlack!!, 2, myWhite!!)

        selectedLabelStyle = LabelStyle.from(R.drawable.marker_unregistered_focused)
            .setTextStyles(20, myBlack, 2, myWhite)

        serverLabelStyle = LabelStyle.from(R.drawable.marker_server)
            .setTextStyles(20, myBlack, 2, myWhite)

        currentLocationStyle = LabelStyle.from(R.drawable.marker_user_location)

        val labelStylesLst = listOf(
            defaultLabelStyle,
            selectedLabelStyle,
            serverLabelStyle,
            currentLocationStyle
        )

        val labelStyles = LabelStyles.from("multiStyle", labelStylesLst)
        map.labelManager?.addLabelStyles(labelStyles)
    }

    /**
     * 현재 위치 마커 추가
     */
    private fun addCurrentLocationMarker(map: KakaoMap, latLng: LatLng) {
        val labelLayer = map.labelManager?.layer

        val label = createLocationLabel(latLng)
        labelLayer?.addLabel(label)
    }

    /**
     * 현재 위치 라벨 생성
     */
    private fun createLocationLabel(latLng: LatLng): LabelOptions {
        return LabelOptions.from(latLng)
            .setStyles(currentLocationStyle)
            .setTag(CURRENT_LOCATION_MARKER_TAG)
    }

    /**
     * 카카오 API 카페 마커 추가
     */
    private fun addCafeMarker(map: KakaoMap, cafes: List<Cafe>) {
        val labelLayer = map.labelManager?.layer
        // labelLayer?.removeAll()

        cafes.forEach { cafe ->
            val label = createCafeLabel(cafe)
            labelLayer?.addLabel(label)
        }
    }

    /**
     * 서버 API 카페 마커 추가
     */
    private fun addServerCafeMarker(map: KakaoMap, cafes: List<ServerCafe>) {
        val labelLayer = map.labelManager?.layer

        cafes.forEach { cafe ->
            val label = createServerCafeLabel(cafe)
            labelLayer?.addLabel(label)
        }
    }

    /**
     * 카카오 API 카페 라벨 생성
     */
    private fun createCafeLabel(cafe: Cafe): LabelOptions {
        val position = LatLng.from(cafe.latitude, cafe.longitude)
        return LabelOptions.from(position)
            .setTexts(LabelTextBuilder().setTexts(cafe.name))
            .setStyles(defaultLabelStyle)
            .setTag(cafe)
    }

    /**
     * 서버 API 카페 라벨 생성
     */
    private fun createServerCafeLabel(cafe: ServerCafe): LabelOptions {
        val position = LatLng.from(cafe.latitude, cafe.longitude)
        return LabelOptions.from(position)
            .setTexts(LabelTextBuilder().setTexts(cafe.cafeName))
            .setStyles(serverLabelStyle)
            .setTag(cafe)
    }

    /**
     * 카페 라벨 클릭 리스너 설정
     */
    private fun setupLabelClickListener(map: KakaoMap) {
        map.setOnLabelClickListener { _, _, label ->
            // Cafe 마커 처리
            val clickedCafe = label.tag as? Cafe
            clickedCafe?.let { viewModel.selectCafe(it) }

            // ServerCafe 마커 처리
            val clickedServerCafe = label.tag as? ServerCafe
            if (clickedServerCafe != null) {
                viewModel.selectServerCafe(clickedServerCafe)
            }

            Log.d("MapFragment", "Label clicked: ${label.tag}")

            // 이전 선택 라벨 원상복귀
            lastSelectedLabel?.setStyles(defaultLabelStyle)

            // 현재 선택된 라벨 스타일 변경
            label.setStyles(selectedLabelStyle)
            label.show(true, 0)
            lastSelectedLabel = label

            true
        }
    }

    /**
     * BottomSheetFragment 생성
     */
    private fun showCafeBottomSheet(cafe: Cafe) {
        val bottomSheet = CafeBottomSheetFragment.newInstance(cafe)
        bottomSheet.show(parentFragmentManager, "CafeBottomSheetFragment")
    }

    private fun showCafeBottomSheet(serverCafe: ServerCafe) {
        val bottomSheet = CafeBottomSheetFragment.newInstance(serverCafe)
        bottomSheet.show(parentFragmentManager, "CafeBottomSheetFragment")
    }

    /**
     * 선택된 카카오 API 카페 옵저빙
     * - BottomSheetFragment 호출
     */
    private fun observeSelectedCafe() {
        viewModel.selectedCafe.observe(viewLifecycleOwner) { cafe ->
            cafe?.let {
                showCafeBottomSheet(it)
                viewModel.clearSelectedCafe()
            }
        }
    }

    /**
     * 선택된 서버 API 카페 옵저빙
     * - BottomSheetFragment 호출
     */
    private fun observeSelectedServerCafe() {
        viewModel.selectedServerCafe.observe(viewLifecycleOwner) { cafe ->
            cafe?.let {
                showCafeBottomSheet(it)
                viewModel.clearSelectedServerCafe()
                Log.d("MapFragment", "서버 카페 선택됨: ${cafe?.cafeName}")
            }
        }
    }

    /**
     * 카카오 API 카페 마커 제거
     */
    private fun removeAllCafeMarkers(map: KakaoMap) {
        val labelLayer = map.labelManager?.layer ?: return
        val labels = labelLayer.allLabels.toList()
        labels.forEach { label ->
            if (label.tag is Cafe) {
                labelLayer.remove(label)
            }
        }
    }

    /**
     * 서버 API 카페 마커 제거
     */
    private fun removeAllServerCafeMarkers(map: KakaoMap) {
        val labelLayer = map.labelManager?.layer ?: return
        val labels = labelLayer.allLabels.toList()
        labels.forEach { label ->
            if (label.tag is ServerCafe) {
                labelLayer.remove(label)
            }
        }
    }

    companion object {
        private const val CURRENT_LOCATION_MARKER_TAG = "currentLocation"
    }

    override fun onResume() {
        super.onResume()
        try {
            binding.mapView.resume()
        } catch (e: NullPointerException) {
            Log.w("MapFragment", "MapView resume 실패 - 아직 초기화되지 않음")
        }
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.pause()
    }
}