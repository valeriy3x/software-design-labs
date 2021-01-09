package by.bsuir.firebasegame.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.bsuir.firebasegame.viewmodels.RoomViewModel

class RoomViewModelFactory: ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomViewModel() as T
    }
}