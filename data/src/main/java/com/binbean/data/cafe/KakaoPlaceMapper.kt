package com.binbean.data.cafe

import com.binbean.domain.cafe.Cafe

fun KakaoPlaceDocument.toCafe(): Cafe {
    return Cafe(
        name = placeName,
        latitude = latitude.toDoubleOrNull() ?: 0.0,
        longitude = longitude.toDoubleOrNull() ?: 0.0,
        address = roadAddressName,
        phone = phone
    )
}
