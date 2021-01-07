package by.bsuir.firebasegame.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.firebasegame.utilities.AuthNavigation
import by.bsuir.firebasegame.utilities.SingleLiveEvent

class AuthViewModel: ViewModel() {
    var navigation = SingleLiveEvent<AuthNavigation>()
    var progress = MutableLiveData<Boolean>(false)

    fun redirectLoginToRegister() {
        navigation.value = AuthNavigation.LoginToRegister
    }

    fun redirectRegisterToLogin() {
        navigation.value = AuthNavigation.RegisterToLogin
    }
}