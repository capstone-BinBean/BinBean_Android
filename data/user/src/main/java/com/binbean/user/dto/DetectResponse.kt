package com.binbean.user.dto

data class PositionF(
    val x: Float,
    val y: Float
)

data class FloorList(
    val borderPosition: List<PositionF>,
    val seatPosition: List<PositionF>,
    val doorPosition: List<PositionF>,
    val counterPosition: List<PositionF>,
    val toiletPosition: List<PositionF>,
    val windowPosition: List<PositionF>
)

data class CurrentSeats(
    val currentPosition: List<PositionF>
)

data class DetectResponse(
    val floorList: FloorList,
    val floorNumber: Int,
    val currentSeats: CurrentSeats
)
