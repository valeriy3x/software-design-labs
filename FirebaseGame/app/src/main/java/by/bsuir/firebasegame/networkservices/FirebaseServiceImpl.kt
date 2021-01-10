package by.bsuir.firebasegame.networkservices

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

object FirebaseServiceImpl: FirebaseService {
    override val profilesPath: String
        get() = "users"

    override val imagesPath: String
        get() = "images/"

    override val roomsPath: String
        get() = "rooms"

    override val playgroundsPath: String
        get() = "playgrounds"

    override val relevantGamePath: String
        get() = "relevantGame"

    override val gamePath: String
        get() = "game"

    override val clientAction: String
        get() = "action"

    override val turnPath: String
        get() = "turnId"

    override val gameArea: String
        get() = "gameArea"

    override val winnerId: String
        get() = "winnerId"

    override val guestId: String
        get() = "guestId"

    override val stats: String
        get() = "stats"

    override val nickname: String
        get() = "nickname"

    override val auth by lazy { FirebaseAuth.getInstance()}

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    override val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

}