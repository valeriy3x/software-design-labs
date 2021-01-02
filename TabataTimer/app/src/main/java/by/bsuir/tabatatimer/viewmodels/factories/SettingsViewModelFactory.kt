package by.bsuir.tabatatimer.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.bsuir.tabatatimer.repositories.Repository
import by.bsuir.tabatatimer.viewmodels.SettingsViewModel

class SettingsViewModelFactory(private val repo: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(repo) as T
    }
}
