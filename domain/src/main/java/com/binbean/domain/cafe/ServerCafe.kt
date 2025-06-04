package com.binbean.domain.cafe

import java.io.Serializable

data class FloorSeat(
    val maxSeats: Int,
    val currentSeats: Int
): Serializable

data class ServerCafe(
    val cafeId: Int,
    val cafeName: String,
    val latitude: Double,
    val longitude: Double,
    val floorSeats: List<FloorSeat>
): Serializable

