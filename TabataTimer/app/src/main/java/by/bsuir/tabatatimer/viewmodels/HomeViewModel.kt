package by.bsuir.tabatatimer.viewmodels

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.repositories.Repository
import by.bsuir.tabatatimer.utilities.HomeNavigation

class HomeViewModel(private val repo: Repository): ViewModel(){
    val dataSet = MutableLiveData<List<Sequence>>()
    val navigation: SingleLiveEvent<HomeNavigation> = SingleLiveEvent()

    init {
        dataSet.value = listOf(Sequence(
            id = 1,
            title = "Workout",
            color = 1,
            prepareTime = 12,
            workTime = 25,
            restTime = 245,
            cycles = 12,
            setsCount = 1,
            restBetweenSets = 0,
            coolDown = 0
        ))
    }

    fun navigateToEdit() {
        navigation.value = HomeNavigation.HomeToEdit
    }
}