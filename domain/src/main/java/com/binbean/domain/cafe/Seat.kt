package com.binbean.domain.cafe

import java.io.Serializable

data class Seat(
    val number: Int,
    val isAvailable: Boolean,
    val isFavorite: Boolean
): Serializable
