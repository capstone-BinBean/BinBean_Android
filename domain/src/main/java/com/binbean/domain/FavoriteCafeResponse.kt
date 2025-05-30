package com.binbean.domain

data class FavoriteCafeResponse(
    val cafeId: Int,
    val cafeName: String,
    val seatsList: List<SeatResponse>
)

data class SeatResponse(
    val seatsNumber: Int,
    val floorNumber: Int,
    val seatsAvailable: Int
)
