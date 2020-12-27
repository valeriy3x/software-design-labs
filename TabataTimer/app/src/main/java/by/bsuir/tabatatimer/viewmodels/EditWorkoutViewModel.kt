package by.bsuir.tabatatimer.viewmodels

import androidx.lifecycle.ViewModel
import by.bsuir.tabatatimer.repositories.Repository

class EditWorkoutViewModel(private val repo: Repository): ViewModel() {
    var color: Int = 2

}