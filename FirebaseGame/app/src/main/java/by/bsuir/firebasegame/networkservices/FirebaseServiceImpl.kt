package by.bsuir.firebasegame.networkservices

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

object FirebaseServiceImpl: FirebaseService {
    override val auth by lazy { FirebaseAuth.getInstance()}

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    override val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

}