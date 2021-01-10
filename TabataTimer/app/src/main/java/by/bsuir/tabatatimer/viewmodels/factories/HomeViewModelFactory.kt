package by.bsuir.tabatatimer.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.bsuir.tabatatimer.repositories.Repository
import by.bsuir.tabatatimer.viewmodels.HomeViewModel

class HomeViewModelFactory(private val repo: Repository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}
