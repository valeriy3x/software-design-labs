package by.bsuir.firebasegame.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.firebasegame.data.viewdata.Playground
import by.bsuir.firebasegame.data.viewdata.Profile
import by.bsuir.firebasegame.data.viewdata.Role
import by.bsuir.firebasegame.data.viewdata.Room
import by.bsuir.firebasegame.networkservices.FirebaseService
import by.bsuir.firebasegame.networkservices.FirebaseServiceImpl
import by.bsuir.firebasegame.utilities.GameNavigation
import by.bsuir.firebasegame.utilities.SingleLiveEvent
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlin.random.Random

class RoomViewModel : ViewModel() {
    lateinit var profile: Profile
    private val webservice: FirebaseService = FirebaseServiceImpl
    private var roomReference = webservice.database.getReference(webservice.roomsPath)
    var roomId: MutableLiveData<String> = MutableLiveData()
    var host: MutableLiveData<Profile> = MutableLiveData()
    var guest: MutableLiveData<Profile> = MutableLiveData()
    var ready: MutableLiveData<Boolean> = MutableLiveData()
    var progress: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorJoinRoom: SingleLiveEvent<String?> = SingleLiveEvent()
    val navigation: SingleLiveEvent<GameNavigation> = SingleLiveEvent()
    val startGameEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    var relevantGameId: String? = null


    fun createGameRoom() {
        progress.value = true
        val rId = Random(System.nanoTime()).nextInt(from = 1, until = 100000).toString()
        roomId.value = rId

        val room = Room(rId, profile, Profile(), "")
        val roomRef = webservice.database.getReference(webservice.roomsPath).child(room.roomId)
        roomRef.setValue(room)
            .addOnSuccessListener {
                progress.value = false
            }
            .continueWith {
                roomReference.child(room.roomId).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val changedRoom = snapshot.getValue<Room>()
                        host.value = changedRoom?.host
                        guest.value = changedRoom?.guest

                        startGameEnabled.value = changedRoom?.host?.id?.isNotEmpty() == true
                                && changedRoom.guest.id.isNotEmpty()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }

        ready.value = false
    }

    fun joinGameRoom(id: String) {
        roomId.value = id
        val roomRef = webservice.database.getReference(webservice.roomsPath).child(id).child(Role.Guest.roleName)
        roomRef.setValue(profile)
            .addOnSuccessListener {
                progress.value = false
            }
            .continueWith {
                roomReference.child(id).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val changedRoom = snapshot.getValue<Room>()

                        if (changedRoom?.relevantGame?.isNotEmpty() == true){
                            relevantGameId = changedRoom.relevantGame
                            navigation.value = GameNavigation.RoomToGame
                        }


                        host.value = changedRoom?.host
                        guest.value = changedRoom?.guest
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
    }

    fun checkRoomExistence(id: String) {
        progress.value = true
        val room = roomReference.orderByKey().equalTo(id)
        room.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progress.value = false
                if (snapshot.exists()) {
                    roomId.value = id
                    navigation.value = GameNavigation.JoinToRoom
                }
                else
                    errorJoinRoom.value = errorJoin
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    fun startGame() {
        val randomId = Random(System.nanoTime()).nextInt(from = 1, until = 100000).toString()
        relevantGameId = randomId
        val playground = Playground(randomId, host.value!!.id, guest.value!!.id, host.value!!.id,
            "", MutableList(3) { MutableList(3) {""} } )


        val gameRef = webservice.database.getReference(webservice.playgroundsPath).child(randomId)
            .child(webservice.gamePath)
        progress.value = true
        gameRef.setValue(playground)
            .addOnSuccessListener {
                progress.value = false
                writeGameId(randomId)
            }
            .addOnFailureListener {
                progress.value = false
            }
    }

    private fun writeGameId(id: String) {
        val roomRef = webservice.database.getReference(webservice.roomsPath).child(roomId.value!!)
            .child(webservice.relevantGamePath)

        progress.value = true
        roomRef.setValue(id)
            .addOnSuccessListener {
                progress.value = false
                navigation.value = GameNavigation.RoomToGame
            }
            .addOnFailureListener {
                progress.value = false
            }
    }

    companion object {
        const val errorJoin = "No such room currently active"
    }

}