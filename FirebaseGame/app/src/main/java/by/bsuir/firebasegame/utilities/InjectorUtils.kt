package by.bsuir.firebasegame.utilities

import by.bsuir.firebasegame.viewmodels.factories.AuthViewModelFactory

object InjectorUtils {
    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        return AuthViewModelFactory()
    }
}