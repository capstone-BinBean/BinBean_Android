package com.binbean.admin.dto

import com.google.gson.annotations.SerializedName

data class Position(val x: Float, val y: Float)

data class FloorWrapper(
    @SerializedName("floorList")
    val floorList: FloorDetail,
    val floorNumber: Int,
    val maxSeats: Int
)

data class FloorDetail(
    val borderPosition: List<Position>,
    val seatPosition: List<Position>,
    val doorPosition: List<Position>,
    val counterPosition: List<Position>,
    val toiletPosition: List<Position>,
    val windowPosition: List<Position>,
    val tablePosition: List<Position>
)

data class CafeRegisterRequest(
    val cafeName: String,
    val cafeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val cafePhone: String,
    val wifiAvailable: Int,
    val chargerAvailable: Int,
    val kidsAvailable: Int,
    val petAvailable: Int,
    val cafeDescription: String,
    val mondayStart: String? = null,
    val mondayEnd: String? = null,
    val tuesdayStart: String? = null,
    val tuesdayEnd: String? = null,
    val wednesdayStart: String? = null,
    val wednesdayEnd: String? = null,
    val thursdayStart: String? = null,
    val thursdayEnd: String? = null,
    val fridayStart: String? = null,
    val fridayEnd: String? = null,
    val saturdayStart: String? = null,
    val saturdayEnd: String? = null,
    val sundayStart: String? = null,
    val sundayEnd: String? = null
)
