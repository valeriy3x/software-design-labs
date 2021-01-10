package by.bsuir.firebasegame.data.gamedata.behaviours
import by.bsuir.firebasegame.data.gamedata.utilities.ClientMarkAction
import by.bsuir.firebasegame.data.gamedata.utilities.PlaygroundListeners
import by.bsuir.firebasegame.data.viewdata.Playground
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ClientBehaviour(
    id : String,
    listener : PlaygroundListeners
): BasicBehaviour(id, listener) {

    override val symbol: String = "O"

    init {
        refGame.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playground = snapshot.getValue<Playground>()

                playground?.let {
                    listener.areaChanged(it.gameArea)
                    isCurrentTurn = (hostId == it.turnId)
                    listener.turnChanged(isCurrentTurn)
                    if(it.winnerId.isNotEmpty()){
                        listener.endGame(it.winnerId == hostId)
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    override fun placeSymbol(row: Int, col: Int) {
        if(isCurrentTurn){
            val action = ClientMarkAction(
                row,
                col,
                symbol,
                hostId
            )
            refAction.setValue(action)
        }
    }
}