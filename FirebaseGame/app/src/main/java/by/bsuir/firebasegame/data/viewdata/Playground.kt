package by.bsuir.firebasegame.data.viewdata

data class Playground(
    val id: String,
    val hostId: String,
    val guestId: String,
    var turnId: String,
    val gameArea: MutableList<MutableList<Char>>
) {
    constructor(): this("", "", "", "", mutableListOf())
}