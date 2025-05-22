package com.binbean.domain.cafe

import java.io.Serializable

data class Cafe(
    val name: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "강원특별자치도 춘천시 강원대학로 1",
    val phone: String = "033-242-1234",
    val startTime: String? = "09:00",
    val endTime: String? = "22:00",

    val id: String? = null,
    val status: CongestionStatus? = null,
    val seats: List<Seat>? = null,

    val wifiAvailable: Int? = 1,
    val chargerAvailable: Int? = 1,
    val petAvailable: Int? = 1,
    val kidsAvailable: Int? = 1,
    val cafeDescription: String? = null,
    val reviewAvg: Double? = null
): Serializable
