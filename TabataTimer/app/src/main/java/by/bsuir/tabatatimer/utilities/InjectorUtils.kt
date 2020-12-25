package by.bsuir.tabatatimer.utilities

import by.bsuir.tabatatimer.repositories.RepositoryImpl
import by.bsuir.tabatatimer.viewmodels.factories.HomeViewModelFactory

object InjectorUtils {

    fun provideHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory(RepositoryImpl)
    }

}