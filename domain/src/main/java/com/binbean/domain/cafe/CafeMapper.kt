package com.binbean.domain.cafe

fun ServerCafe.toCafe(): Cafe {
    return Cafe(
        id = this.cafeId,
        name = this.cafeName,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}