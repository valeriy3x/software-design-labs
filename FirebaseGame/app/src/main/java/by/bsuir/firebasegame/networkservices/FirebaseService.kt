package by.bsuir.firebasegame.networkservices

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

interface FirebaseService {

    val profilesPath: String
    val imagesPath: String
    val auth: FirebaseAuth
    val currentUser: FirebaseUser?
    val storage: FirebaseStorage
    val database: FirebaseDatabase
}