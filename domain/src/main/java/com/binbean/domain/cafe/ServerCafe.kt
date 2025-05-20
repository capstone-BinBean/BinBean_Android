package com.binbean.domain.cafe

data class FloorSeat(
    val maxSeats: Int,
    val currentSeats: Int
)

data class ServerCafe(
    val cafeId: Int,
    val cafeName: String,
    val latitude: Double,
    val longitude: Double,
    val floorSeats: List<FloorSeat>
)

