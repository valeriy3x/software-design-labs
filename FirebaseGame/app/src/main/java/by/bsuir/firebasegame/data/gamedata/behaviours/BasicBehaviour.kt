package by.bsuir.firebasegame.data.gamedata.behaviours

import by.bsuir.firebasegame.data.gamedata.utilities.PlaygroundListeners
import by.bsuir.firebasegame.networkservices.FirebaseService
import by.bsuir.firebasegame.networkservices.FirebaseServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

abstract class BasicBehaviour(
    protected val playgroundId: String,
    protected val listener: PlaygroundListeners
) {
    protected val webservice: FirebaseService = FirebaseServiceImpl

    protected val refGame: DatabaseReference =
        webservice.database.getReference(webservice.playgroundsPath).child(playgroundId).child(webservice.gamePath)

    protected val refAction: DatabaseReference =
        webservice.database.getReference(webservice.playgroundsPath).child(playgroundId).child(webservice.clientAction)

    protected val refTurn = refGame.child(webservice.turnPath)


    protected abstract val symbol: String
    abstract fun placeSymbol(row: Int, col: Int)

    protected var isCurrentTurn: Boolean = false
    protected val hostId: String = webservice.currentUser?.uid!!

}