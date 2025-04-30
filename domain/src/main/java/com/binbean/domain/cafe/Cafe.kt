package com.binbean.domain.cafe

import java.io.Serializable

data class Cafe(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val phone: String? = null
): Serializable
