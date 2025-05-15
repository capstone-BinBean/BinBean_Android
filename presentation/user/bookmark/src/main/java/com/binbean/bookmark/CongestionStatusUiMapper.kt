package com.binbean.bookmark

import android.content.Context
import androidx.core.content.ContextCompat
import com.binbean.domain.cafe.CongestionStatus

fun CongestionStatus?.toStatusText(): String = when (this) {
    CongestionStatus.FREE -> "여유"
    CongestionStatus.NORMAL -> "보통"
    CongestionStatus.BUSY -> "혼잡"
    null -> "여유"
}

fun CongestionStatus?.toStatusColorRes(): Int = when (this) {
    CongestionStatus.FREE -> R.color.status_free
    CongestionStatus.NORMAL -> R.color.status_normal
    CongestionStatus.BUSY -> R.color.status_busy
    null -> R.color.status_free
}

fun CongestionStatus?.toStatusColor(context: Context): Int {
    return ContextCompat.getColor(context, this.toStatusColorRes())
}

fun CongestionStatus?.toIconRes(): Int = when (this) {
    CongestionStatus.FREE -> R.drawable.ic_status_green
    CongestionStatus.NORMAL -> R.drawable.ic_status_yellow
    CongestionStatus.BUSY -> R.drawable.ic_status_red
    null -> R.drawable.ic_status_green
}