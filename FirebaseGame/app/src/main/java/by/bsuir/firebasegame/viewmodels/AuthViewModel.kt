package by.bsuir.firebasegame.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.navArgument
import by.bsuir.firebasegame.networkservices.FirebaseService
import by.bsuir.firebasegame.networkservices.FirebaseServiceImpl
import by.bsuir.firebasegame.utilities.AuthNavigation
import by.bsuir.firebasegame.utilities.SingleLiveEvent
import com.google.android.play.core.tasks.OnCompleteListener
import com.google.android.play.core.tasks.Task
import com.google.firebase.auth.AuthResult

class AuthViewModel: ViewModel() {
    val webservice: FirebaseService = FirebaseServiceImpl
    var navigation = SingleLiveEvent<AuthNavigation>()
    var progress = MutableLiveData<Boolean>(false)
    val errorMessageEmail: SingleLiveEvent<String?> = SingleLiveEvent()
    val errorMessagePassword: SingleLiveEvent<String?> = SingleLiveEvent()
    val errorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    
    fun register(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            progress.value = true
            webservice.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    progress.value = false
                    navigation.value = AuthNavigation.RegisterToAccount
                }
                .addOnFailureListener {
                    progress.value = false
                    errorMessage.value = it.message
                }
        }
        validateCredentials(email, password)
    }

    fun login(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            progress.value = true
            webservice.auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    progress.value = false
                    navigation.value = AuthNavigation.LoginToAccount
                }
                .addOnFailureListener {
                    progress.value = false
                    errorMessage.value = it.message //TODO: Parse an error somehow
                }
        }
        validateCredentials(email, password)
    }

    private fun validateCredentials(email: String, password: String) {
        errorMessageEmail.value = if (email.isEmpty()) emptyLinesErrorMessage else null
        errorMessagePassword.value = if (password.isEmpty()) emptyLinesErrorMessage else null
        // TODO:Fucking single live event doesn't work in this case, fix later
    }

    fun redirectLoginToRegister() {
        navigation.value = AuthNavigation.LoginToRegister
    }

    fun redirectRegisterToLogin() {
        navigation.value = AuthNavigation.RegisterToLogin
    }

    companion object {
        private const val emptyLinesErrorMessage = "Blank line is deprecated!"
        private const val globalErrorMessage = "Something went wrong! Sorry :("
    }
}