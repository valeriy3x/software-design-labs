package by.bsuir.firebasegame.utilities

import by.bsuir.firebasegame.viewmodels.factories.AccountViewModelFactory
import by.bsuir.firebasegame.viewmodels.factories.AuthViewModelFactory
import by.bsuir.firebasegame.viewmodels.factories.EditViewModelFactory

object InjectorUtils {
    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        return AuthViewModelFactory()
    }

    fun provideEditViewModelFactory(): EditViewModelFactory {
        return EditViewModelFactory()
    }

    fun provideAccountViewModelFactory(): AccountViewModelFactory {
        return AccountViewModelFactory()
    }
}