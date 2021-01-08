package by.bsuir.firebasegame.data.viewdata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    val id: String,
    val avatar: String,
    val nickname: String
    ) : Parcelable
{
    constructor() : this("", "", "")
}