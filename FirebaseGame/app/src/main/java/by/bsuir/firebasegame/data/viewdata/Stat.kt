package by.bsuir.firebasegame.data.viewdata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stat (
    var gameId: String,
    var opponentNick: String,
    var won: Boolean): Parcelable
{
    constructor(): this("", "", false) {

    }
}