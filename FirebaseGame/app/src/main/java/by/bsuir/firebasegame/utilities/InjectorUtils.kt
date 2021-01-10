package by.bsuir.firebasegame.utilities

import by.bsuir.firebasegame.viewmodels.PlaygroundViewModel
import by.bsuir.firebasegame.viewmodels.factories.*

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

    fun provideRoomViewModelFactory(): RoomViewModelFactory {
        return RoomViewModelFactory()
    }

    fun providePlaygroundViewModelFactory(): PlaygroundViewModelFactory {
        return PlaygroundViewModelFactory()
    }
}