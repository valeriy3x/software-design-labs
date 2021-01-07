package by.bsuir.firebasegame.networkservices

import com.google.firebase.auth.FirebaseAuth

object FirebaseServiceImpl: FirebaseService {
    override val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()
}