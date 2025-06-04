package com.binbean.domain.cafe

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    @SerializedName("reviewAvg")
    val reviewAvgRaw: String? = null,
    val reviewResponse: List<Review>,
    val floorPlanId: List<FloorPlan>
): Serializable {
    val reviewAvg: Double?
        get() = reviewAvgRaw?.toDoubleOrNull()
}

data class CafeImage(
    val url: String
): Serializable

data class FloorPlan(
    val id: Int
): Serializable