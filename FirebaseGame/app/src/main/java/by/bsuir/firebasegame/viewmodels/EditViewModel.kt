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
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or

class EditViewModel : ViewModel() {
    private val webservice: FirebaseService = FirebaseServiceImpl
    var nickname: MutableLiveData<String> = MutableLiveData()
    var avatar: MutableLiveData<Bitmap> = MutableLiveData()
    var avatarUrl: MutableLiveData<Uri> = MutableLiveData()
    var avatarWeb: MutableLiveData<String> = MutableLiveData()
    private var profile: Profile? = null
    var progressProfile = MutableLiveData(false)
    var progressAvatar = MutableLiveData(false)
    var navigation: SingleLiveEvent<GameNavigation> = SingleLiveEvent()
    var nicknameErrorMessage = SingleLiveEvent<String?>()
    var globalErrorMessage = SingleLiveEvent<String?>()
    var usingGravatar = false


    fun fillFields(profile: Profile) {
        nickname.value = profile.nickname
        avatarWeb.value = profile.avatar

        this.profile = profile
    }


    fun createOrEditProfile() {
        val nick = nickname.value ?: ""

        if (nick.isNotEmpty()) {

            if (profile != null && avatarUrl.value == null) {
                profile?.let {
                    progressProfile.value = true
                    uploadProfileData(it.id, it.avatar, nick)
                }

                return
            }

            val userId = FirebaseServiceImpl.currentUser?.uid ?: ""
            val url = UUID.randomUUID().toString()

            progressProfile.value = true
            progressAvatar.value = true

            if (usingGravatar) {
                uploadProfileData(userId, avatarWeb.value!!, nick)
            }

            else {

                val storageRef = webservice.storage.reference.child(webservice.imagesPath + url)
                avatarUrl.value?.let {
                    storageRef.putFile(it)
                        .addOnSuccessListener {
                            progressAvatar.value = false
                        }
                        .continueWith {
                            storageRef.downloadUrl.addOnSuccessListener { uri ->
                                uploadProfileData(userId, uri.toString(), nick)
                            }
                        }
                        .addOnFailureListener { ex ->
                            progressAvatar.value = false
                            globalErrorMessage.value = ex.message
                        }


                }
            }
        } else {
            nicknameErrorMessage.value = nicknameBlankMessage
        }
    }

    private fun uploadProfileData(id: String, avatar: String, nick: String) {
        val ref = webservice.database.getReference(webservice.profilesPath).child(id)

        val profile = Profile(id, avatar, nick, mutableListOf())

        ref.setValue(profile)
            .addOnSuccessListener {
                progressProfile.value = false

                navigation.value = GameNavigation.EditToAccount
            }
            .addOnFailureListener {
                progressProfile.value = false
                globalErrorMessage.value = it.message
            }
    }

    fun changeAvatar() {
        navigation.value = GameNavigation.EditToPhotoSelector
    }

    fun useGravatar() {
        val email = webservice.currentUser?.email!!
        val hash = hex(email)
        avatarWeb.value = "$gravatarUrl$hash?s=80"
        usingGravatar = true
    }

    private fun hex(mail: String): String? {
        var result: String? = null

        try {
            val digest =
                MessageDigest.getInstance("MD5")
            digest.reset()
            digest.update(mail.toByteArray())
            val bigInt = BigInteger(1, digest.digest())
            result = bigInt.toString(16)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return result
    }

    companion object {
        private const val nicknameBlankMessage = "Nickname field couldn't be empty"
        private const val gravatarUrl = "https://s.gravatar.com/avatar/"
    }
}
