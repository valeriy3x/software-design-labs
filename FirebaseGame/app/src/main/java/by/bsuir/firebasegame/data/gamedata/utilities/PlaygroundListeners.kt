package by.bsuir.firebasegame.data.gamedata.utilities

interface PlaygroundListeners {
    fun cellChanged(row: Int, col: Int, symbol: String)
    fun areaChanged(area: MutableList<MutableList<String>>)
    fun turnChanged(currentUserTurn: Boolean)
    fun endGame(areYouWinningSon: Boolean)
}