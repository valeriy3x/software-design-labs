package by.bsuir.firebasegame.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.navArgument
import by.bsuir.firebasegame.utilities.AuthNavigation
import by.bsuir.firebasegame.utilities.SingleLiveEvent

class AuthViewModel: ViewModel() {
    var navigation = SingleLiveEvent<AuthNavigation>()
    var progress = MutableLiveData<Boolean>(false)
    val errorMessageEmail: SingleLiveEvent<String?> = SingleLiveEvent()
    val errorMessagePassword: SingleLiveEvent<String?> = SingleLiveEvent()
    
    fun register(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            //register
        }
        validateCredentials(email, password)
    }

    fun login(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            //Login
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
    }
}