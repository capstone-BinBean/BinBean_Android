package com.binbean.user.dto

data class Position(val x: Int, val y: Int)

data class DetectRequest(
    val borderPosition: List<Position>,
    val seatPosition: List<Position>,
    val doorPosition: List<Position>,
    val counterPosition: List<Position>,
    val toiletPosition: List<Position>,
    val windowPosition: List<Position>,
    val floorNumber: Int
)
