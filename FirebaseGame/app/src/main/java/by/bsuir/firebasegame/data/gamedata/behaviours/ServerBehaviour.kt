package by.bsuir.firebasegame.data.gamedata.behaviours

import by.bsuir.firebasegame.data.gamedata.utilities.ClientMarkAction
import by.bsuir.firebasegame.data.gamedata.utilities.PlaygroundListeners
import by.bsuir.firebasegame.data.viewdata.Profile
import by.bsuir.firebasegame.data.viewdata.Stat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class HostBehaviour(
    gameId : String,
    listener : PlaygroundListeners
): BasicBehaviour(gameId, listener) {

    override val symbol: String
        get() = "X"

    private var area = MutableList(3) { MutableList(3) {""} }

    private var hostNickname: String = ""
    private var guestId : String = ""
    private var guestNickname: String = ""


    init {
        refAction.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value == null)
                    return
                val action = snapshot.getValue<ClientMarkAction>()
                action?.let {
                    if (area[it.row][it.col].isEmpty()) {
                        markCell(it.row, it.col, it.symbol)
                        changeTurn(hostId)
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        refTurn.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                isCurrentTurn = snapshot.value as String == hostId
                listener.turnChanged(isCurrentTurn)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        refGame.child(webservice.guestId).addValueEventListener(object :  ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                guestId = snapshot.value.toString()

                val refEn = webservice.database.reference.child(webservice.profilesPath).child(guestId).child(webservice.nickname)
                refEn.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        guestNickname = snapshot.value.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val ref = webservice.database.reference.child(webservice.profilesPath).child(hostId).child(webservice.nickname)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hostNickname = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        refGame.child(webservice.winnerId).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val winnerId = snapshot.value.toString()

                if(winnerId.isNotEmpty()){
                    listener.endGame(winnerId == hostId)
                }
            }

        })
    }


    private fun checkGameState(){
        refGame.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hashMap = snapshot.value as HashMap<*,*>
                val field = hashMap[webservice.gameArea] as MutableList<MutableList<String>>

                if(field[0][0].isNotEmpty() && field[0][0] == field[1][1] && field[1][1] == field[2][2])
                    setWinner(field[0][0])

                if(field[0][0].isNotEmpty() && field[0][0] == field[1][0] && field[1][0] == field[2][0])
                    setWinner(field[0][0])

                if(field[1][0].isNotEmpty() && field[1][0] == field[1][1] && field[1][1] == field[1][2])
                    setWinner(field[1][0])

                if(field[0][0].isNotEmpty() && field[0][0] == field[0][1] && field[0][1] == field[0][2])
                    setWinner(field[0][0])

                if(field[2][0].isNotEmpty() && field[2][0] == field[2][1] && field[2][1] == field[2][2])
                    setWinner(field[2][0])

                if(field[0][2].isNotEmpty() && field[0][2] == field[1][1] && field[1][1] == field[2][0])
                    setWinner(field[0][2])

                if(field[0][1].isNotEmpty() && field[0][1] == field[1][1] && field[1][1] == field[2][1])
                    setWinner(field[0][1])

                if(field[0][2].isNotEmpty() && field[0][2] == field[1][2] && field[1][2] == field[2][2])
                    setWinner(field[0][2])
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


    private fun setWinner(sym: String){
        var userWinner: String = ""
        var userLoser: String = ""
        var nickWinner = ""
        var nickLoser = ""

        if (symbol == sym) {
            userWinner = hostId
            userLoser = guestId
            nickWinner = hostNickname
            nickLoser = guestNickname
        }
        else {
            userWinner = guestId
            userLoser = hostId
            nickWinner = guestNickname
            nickLoser = hostNickname
        }

        refGame.child(webservice.winnerId).setValue(userWinner)



        val refWinner = webservice.database.reference.child(webservice.profilesPath).child(userWinner)
        refWinner.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hashMap = snapshot.value as HashMap<*,*>

                val stat = Stat(playgroundId, nickLoser, true)

                if (hashMap[webservice.stats] == null) {
                    var list = mutableListOf(stat)
                    refWinner.child(webservice.stats).setValue(list)
                }
                else {
                    var profile = snapshot.getValue<Profile>()
                    val list = profile?.stats

                    list?.add(0, stat)

                    refWinner.child(webservice.stats).setValue(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val refLoser = webservice.database.reference.child(webservice.profilesPath).child(userLoser)
        refLoser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hashMap = snapshot.value as HashMap<*,*>
                val stat = Stat(playgroundId, nickWinner, false)

                if (hashMap[webservice.stats] == null) {
                    var list = mutableListOf(stat)
                    refLoser.child(webservice.stats).setValue(list)
                }
                else {
                    var profile = snapshot.getValue<Profile>()
                    val list = profile?.stats

                    list?.add(0, stat)

                    refLoser.child(webservice.stats).setValue(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun placeSymbol(row: Int, col: Int) {
        if(area[row][col].isEmpty()){
            markCell(row, col, symbol)
            changeTurn(guestId)
        }
    }


    private fun markCell(row : Int, col : Int, symbol : String){
        area[row][col] = symbol
        listener.cellChanged(row, col, symbol)
        refGame.child(webservice.gameArea).setValue(area)
        checkGameState()
    }

    private fun changeTurn(uid : String){
        refTurn.setValue(uid)
    }

}