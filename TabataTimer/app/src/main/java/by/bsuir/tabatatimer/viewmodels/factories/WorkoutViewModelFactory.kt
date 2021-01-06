package by.bsuir.tabatatimer.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.bsuir.tabatatimer.repositories.Repository
import by.bsuir.tabatatimer.viewmodels.WorkoutViewModel

class WorkoutViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WorkoutViewModel(repository) as T
    }
}