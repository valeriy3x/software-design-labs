package by.bsuir.firebasegame.data.viewdata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    val id: String,
    val avatar: String,
    val nickname: String,
    val stats: MutableList<Stat>
    ) : Parcelable
{
    constructor() : this("", "", "", mutableListOf())
}