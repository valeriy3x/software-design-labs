package by.bsuir.firebasegame.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.firebasegame.data.viewdata.Profile
import by.bsuir.firebasegame.networkservices.FirebaseService
import by.bsuir.firebasegame.networkservices.FirebaseServiceImpl
import by.bsuir.firebasegame.utilities.GameNavigation
import by.bsuir.firebasegame.utilities.SingleLiveEvent
import io.reactivex.Completable
import java.util.*

class EditViewModel: ViewModel() {
    private val webservice: FirebaseService = FirebaseServiceImpl
    var nickname: MutableLiveData<String> = MutableLiveData()
    var avatar: MutableLiveData<Bitmap> = MutableLiveData()
    var avatarUrl: MutableLiveData<Uri> = MutableLiveData()

    var progressProfile = MutableLiveData(false)
    var progressAvatar = MutableLiveData(false)
    var navigation: SingleLiveEvent<GameNavigation> = SingleLiveEvent()
    var nicknameErrorMessage = SingleLiveEvent<String?>()
    var globalErrorMessage = SingleLiveEvent<String?>()

    fun createOrEditProfile() {
        val nick = nickname.value ?: ""

        if (nick.isNotEmpty()) {

            val userId = FirebaseServiceImpl.currentUser?.uid ?: ""
            val avatarUrl = UUID.randomUUID().toString()

            val profile = Profile(userId, avatarUrl, nick)

            progressProfile.value = true
            progressAvatar.value = true
            val ref = webservice.database.getReference("users").child(userId)


            ref.setValue(profile)
                .addOnSuccessListener {
                    progressProfile.value = false
                }
                .addOnFailureListener {
                    progressProfile.value = false
                    globalErrorMessage.value = it.message
                }
                .continueWith {
                    uploadAvatarToStorage(avatarUrl)
                }
        }
        else {
            nicknameErrorMessage.value = nicknameBlankMessage
        }
    }

    private fun uploadAvatarToStorage(url: String) {
        val storageRef = webservice.storage.reference.child("images/" + url)
        avatarUrl.value?.let {
            storageRef.putFile(it)
                .addOnSuccessListener {
                    progressAvatar.value = false
                    navigation.value = GameNavigation.EditToAccount
                }
                .addOnFailureListener { ex ->
                    progressAvatar.value = false
                    globalErrorMessage.value = ex.message
                }

        }
    }

    fun changeAvatar() {
        navigation.value = GameNavigation.EditToPhotoSelector
    }

    companion object {
        private const val nicknameBlankMessage = "Nickname field couldn't be empty"
    }
}