package by.bsuir.tabatatimer.utilities

import by.bsuir.tabatatimer.repositories.RepositoryImpl
import by.bsuir.tabatatimer.viewmodels.factories.EditWorkoutViewModelFactory
import by.bsuir.tabatatimer.viewmodels.factories.HomeViewModelFactory
import by.bsuir.tabatatimer.viewmodels.factories.SettingsViewModelFactory

object InjectorUtils {

    fun provideHomeViewModelFactory(): HomeViewModelFactory {
        return HomeViewModelFactory(RepositoryImpl)
    }

    fun provideEditWorkoutViewModelFactory(): EditWorkoutViewModelFactory {
        return EditWorkoutViewModelFactory(RepositoryImpl)
    }

    fun provideSettingsViewModelFactory(): SettingsViewModelFactory {
        return SettingsViewModelFactory(RepositoryImpl)
    }

}