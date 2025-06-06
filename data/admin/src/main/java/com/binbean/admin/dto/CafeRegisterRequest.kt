package com.binbean.admin.dto

data class Position(val x: Int, val y: Int)

data class FloorList(
    val borderPosition: List<Position>,
    val seatPosition: List<Position>,
    val doorPosition: List<Position>,
    val counterPosition: List<Position>,
    val toiletPosition: List<Position>,
    val windowPosition: List<Position>
)

data class Floor(
    val floorList: FloorList,
    val floorNumber: Int,
    val maxSeats: Int
)

data class CafeImageUrl(
    val url: String
)

data class CafeRegisterRequest(
    val cafeName: String,
    val cafeAddress: String,
    val latitude: Double,
    val longitude: Double,
    val cafePhone: String,
    val cafeImgUrl: List<CafeImageUrl>,
    val wifiAvailable: Int,
    val chargerAvailable: Int,
    val kidsAvailable: Int,
    val petAvailable: Int,
    val cafeDescription: String,
    val floorList: List<Floor>,
    val monday_start: String? = null,
    val monday_end: String? = null,
    val tuesday_start: String? = null,
    val tuesday_end: String? = null,
    val wednesday_start: String? = null,
    val wednesday_end: String? = null,
    val thursday_start: String? = null,
    val thursday_end: String? = null,
    val friday_start: String? = null,
    val friday_end: String? = null,
    val saturday_start: String? = null,
    val saturday_end: String? = null,
    val sunday_start: String? = null,
    val sunday_end: String? = null
)
