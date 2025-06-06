package com.binbean.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binbean.domain.cafe.Cafe
import com.binbean.domain.cafe.CafeInfoImgItem

class CafeBottomSheetViewModel : ViewModel() {
    private val _cafe = MutableLiveData<Cafe>()
    val cafe: LiveData<Cafe> get() = _cafe

    private val _photoList = MutableLiveData<List<CafeInfoImgItem>>()
    val photoList: LiveData<List<CafeInfoImgItem>> = _photoList

    fun setCafe(cafe: Cafe) {
        _cafe.value = cafe
    }

    fun loadCafeInfoImg() {
        _photoList.value = listOf(
            CafeInfoImgItem(IMG1),
            CafeInfoImgItem(IMG2),
            CafeInfoImgItem(IMG3),
            CafeInfoImgItem(IMG4),
            CafeInfoImgItem(IMG5)
        )
    }

    companion object{
        const val IMG1 = "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/3fuW/image/3znmV2W5zH-_v8uDNk1Yvgrhflk"
        const val IMG2 = "https://cdn.st-news.co.kr/news/photo/202301/6854_20623_5753.jpg"
        const val IMG3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaaTb8c9SXDEyRXoSKmo2RzoPNvpezNJrTvw&s"
        const val IMG4 = "https://mblogthumb-phinf.pstatic.net/MjAyMDExMTZfOTgg/MDAxNjA1NTA3ODAzMTA0.k6DOqsIrLmGlb6APiaZ8sJROiEOLVHDe4RfdcNBWbJ8g.s2tFn0NQM65zkyMFi93KU8sQXe7Ll1oNuxSTbbSv0RQg.JPEG.choee93/SE-3aaf15a5-9bdb-44ad-bc36-2c0fe8d7667a.jpg?type=w800"
        const val IMG5 = "https://blog.kakaocdn.net/dn/yhbVA/btr1bewkFbF/B5CaEIl78bJWQ1crQKASR1/img.jpg"
    }
}