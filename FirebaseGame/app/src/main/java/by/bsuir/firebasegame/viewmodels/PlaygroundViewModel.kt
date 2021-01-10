package by.bsuir.firebasegame.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.firebasegame.data.gamedata.behaviours.ClientBehaviour
import by.bsuir.firebasegame.data.gamedata.behaviours.HostBehaviour
import by.bsuir.firebasegame.data.gamedata.behaviours.BasicBehaviour
import by.bsuir.firebasegame.data.gamedata.utilities.PlaygroundListeners
import by.bsuir.firebasegame.data.viewdata.Role
import by.bsuir.firebasegame.utilities.SingleLiveEvent

class PlaygroundViewModel: ViewModel(){

    private lateinit var behaviour : BasicBehaviour
    var message: MutableLiveData<String> = MutableLiveData()
    var gameArea : MutableLiveData<MutableList<MutableList<String>>> = MutableLiveData(MutableList(3) {MutableList(3) {""} })
    var turn: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var endGame: SingleLiveEvent<Boolean> = SingleLiveEvent()


    fun startGame(id: String, role: Role)  {

        val fuckingGameListener = object : PlaygroundListeners {
            override fun cellChanged(row: Int, col: Int, symbol: String) {
                val list = mutableListOf<MutableList<String>>()
                list.addAll(gameArea.value!!)
                list[row][col] = symbol
                gameArea.value = list
            }

            override fun areaChanged(area: MutableList<MutableList<String>>) {
                gameArea.value = area
            }

            override fun turnChanged(currentUserTurn: Boolean) {
                turn.value = currentUserTurn
            }

            override fun endGame(areYouWinningSon: Boolean) {
                endGame.value = areYouWinningSon
            }

        }

        behaviour = when(role) {
            Role.Host -> HostBehaviour(id, fuckingGameListener)
            Role.Guest -> ClientBehaviour(id, fuckingGameListener)
        }
    }

    fun markCell(row: Int, col: Int){
        if (turn.value == true)
            behaviour.placeSymbol(row, col)
        else
            message.value = errorNotYourTurn
    }

    companion object {
        private const val errorNotYourTurn = "It's not your turn yet."
    }

}