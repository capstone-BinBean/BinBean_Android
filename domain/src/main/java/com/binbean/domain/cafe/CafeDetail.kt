package com.binbean.domain.cafe

import com.google.gson.annotations.SerializedName

data class CafeDetail(
    @SerializedName("cafeId")
    val id: Int,
    val cafeName: String,
    val cafeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val startTime: String,
    val endTime: String,
    val cafePhone: String,
    val wifiAvailable: Int,
    val chargerAvailable: Int,
    val petAvailable: Int,
    val kidsAvailable: Int,
    val cafeDescription: String,
    val cafeImgUrl: List<CafeImage>,
    val reviewAvg: String,
    val reviewResponse: List<Any>,
    val floorPlanId: List<FloorPlan>
)

data class CafeImage(
    val url: String
)

data class FloorPlan(
    val id: Int
)
