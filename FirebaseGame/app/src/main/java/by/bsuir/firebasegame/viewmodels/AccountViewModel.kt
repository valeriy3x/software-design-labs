package by.bsuir.firebasegame.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.firebasegame.data.viewdata.Profile
import by.bsuir.firebasegame.networkservices.FirebaseService
import by.bsuir.firebasegame.networkservices.FirebaseServiceImpl
import by.bsuir.firebasegame.utilities.GameNavigation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class AccountViewModel: ViewModel() {
    private val webservice: FirebaseService = FirebaseServiceImpl
    var profile: Profile? = null
    val nickname: MutableLiveData<String> = MutableLiveData()
    val avatarUrl: MutableLiveData<String> = MutableLiveData()
    val navigation: MutableLiveData<GameNavigation> = MutableLiveData()
    val progress: MutableLiveData<Boolean> = MutableLiveData(false)


    fun fillData() {
        progress.value = true
        val userId = webservice.currentUser?.uid ?: ""

        val ref = webservice.database.getReference("users").child(userId)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profile = snapshot.getValue<Profile>()
                nickname.value = profile?.nickname
                avatarUrl.value = profile?.avatar
                progress.value = false // TODO : Avatar downloads too slow
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun logout() {
        webservice.auth.signOut()
        navigation.value = GameNavigation.AccountToLogin
    }

    fun editProfile() {
        navigation.value = GameNavigation.AccountToEdit
    }
}