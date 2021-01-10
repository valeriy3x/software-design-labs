package by.bsuir.firebasegame.data.viewdata

data class Room(
    val roomId: String,
    val host: Profile,
    val guest: Profile,
    var relevantGame: String // true if was started, false if not
) {
    constructor(): this("", Profile(), Profile(), "")
}