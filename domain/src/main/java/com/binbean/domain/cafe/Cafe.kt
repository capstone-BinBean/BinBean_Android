package com.binbean.domain.cafe

import java.io.Serializable

data class Cafe(
    val name: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String? = null,
    val phone: String? = null,

    val id: String? = null,
    val status: CongestionStatus? = null,
    val seats: List<Seat>? = null
): Serializable
