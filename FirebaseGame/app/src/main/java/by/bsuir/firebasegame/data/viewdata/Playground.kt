package by.bsuir.firebasegame.data.viewdata

data class Playground(
    val id: String,
    var hostId: String,
    var guestId: String,
    var turnId: String,
    var winnerId: String,
    var gameArea: MutableList<MutableList<String>>
) {
    constructor(): this("", "", "", "","", mutableListOf())
}