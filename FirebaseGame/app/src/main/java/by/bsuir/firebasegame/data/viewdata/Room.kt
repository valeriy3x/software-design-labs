package by.bsuir.firebasegame.data.viewdata

data class Room(
    val roomId: String,
    val host: Profile,
    val guest: Profile,
) {
    constructor(): this("", Profile(), Profile())
}