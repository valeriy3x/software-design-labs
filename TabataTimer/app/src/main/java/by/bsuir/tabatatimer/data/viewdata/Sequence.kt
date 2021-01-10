package by.bsuir.tabatatimer.data.viewdata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sequence(
    var id: Int?,
    val title: String?,
    val color: Int?,
    val prepareTime: Int?,
    val workTime: Int?,
    val restTime: Int?,
    val cycles: Int?,
    val setsCount: Int?,
    val restBetweenSets: Int?,
    val coolDown: Int?
) : Parcelable