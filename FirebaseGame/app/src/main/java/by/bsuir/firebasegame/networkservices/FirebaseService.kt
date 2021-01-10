package by.bsuir.firebasegame.networkservices

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

interface FirebaseService {

    val profilesPath: String
    val imagesPath: String
    val roomsPath: String
    val gamePath: String
    val clientAction: String
    val turnPath: String
    val gameArea: String
    val winnerId: String

    val nickname: String
    val guestId: String
    val stats: String

    val playgroundsPath: String
    val relevantGamePath: String
    val auth: FirebaseAuth
    val currentUser: FirebaseUser?
    val storage: FirebaseStorage
    val database: FirebaseDatabase
}