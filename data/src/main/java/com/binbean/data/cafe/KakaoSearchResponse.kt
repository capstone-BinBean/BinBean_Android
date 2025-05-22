package com.binbean.data.cafe

import com.google.gson.annotations.SerializedName

data class KakaoSearchResponse(
    @SerializedName("documents")
    val documents: List<KakaoPlaceDocument>,

    @SerializedName("meta")
    val meta: KakaoMeta
)

data class KakaoPlaceDocument(
    @SerializedName("place_name")
    val placeName: String,

    @SerializedName("x")
    val longitude: String,

    @SerializedName("y")
    val latitude: String,

    @SerializedName("road_address_name")
    val roadAddressName: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("place_url")
    val placeUrl: String?
)

data class KakaoMeta(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("pageable_count")
    val pageableCount: Int,

    @SerializedName("is_end")
    val isEnd: Boolean
)

