package by.bsuir.firebasegame.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.bsuir.firebasegame.viewmodels.EditViewModel

class EditViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditViewModel() as T
    }
}