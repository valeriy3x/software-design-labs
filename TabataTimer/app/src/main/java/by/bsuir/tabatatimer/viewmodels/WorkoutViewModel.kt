package by.bsuir.tabatatimer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.tabatatimer.repositories.Repository
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.data.viewdata.helpers.StepManager
import by.bsuir.tabatatimer.utilities.HomeNavigation
import by.bsuir.tabatatimer.utilities.WorkoutResourceHelper
import by.bsuir.tabatatimer.utilities.WorkoutResourceHelperImpl


class WorkoutViewModel(private val repo: Repository) : ViewModel() {
    private val workoutResourceHelper: WorkoutResourceHelper = WorkoutResourceHelperImpl
    var navigation: SingleLiveEvent<HomeNavigation> = SingleLiveEvent()
    val errorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    var running: MutableLiveData<Boolean> = MutableLiveData(false)
    var currentTime: MutableLiveData<String> = MutableLiveData()
    var currentStep: MutableLiveData<String> = MutableLiveData()
    var stepManager: SingleLiveEvent<StepManager> = SingleLiveEvent()
    val durations: MutableList<Int?> = mutableListOf()
    val steps: MutableList<String?> = mutableListOf()
    var active: MutableLiveData<Boolean> = MutableLiveData(false)
    var currentItemIndex: MutableLiveData<Int> = MutableLiveData(0)
    var sequence: Sequence? = null
        set(value) {
            field = value
            fillLists()
        }


    private fun fillLists() {
        sequence?.let {
            if (it.setsCount != null && it.cycles != null) {

                // Add Preparation Time Here

                durations.add(it.prepareTime)
                steps.add(workoutResourceHelper.prepareString)

                // Add Sets of Work+Rest Cycles Here

                for (set in 0 until it.setsCount) {
                    for (cycle in 0 until it.cycles) {
                        durations.add(it.workTime)
                        steps.add(workoutResourceHelper.workString)

                        durations.add(it.restTime)
                        steps.add(workoutResourceHelper.restString)
                    }

                    if (set != it.setsCount - 1 && it.restBetweenSets != 0) {
                        durations.add(it.restBetweenSets)
                        steps.add(workoutResourceHelper.restBetweenSetsString)
                    }
                }

                // Add Cool Down Here (finally some good freakin' балдёж)
                if (it.coolDown != 0) {
                    durations.add(it.coolDown)
                    steps.add(workoutResourceHelper.coolDownString)
                }
                currentTime.value = durations[0].toString()
                currentStep.value = steps[0]
            }
        }
    }

    fun nextStep() {
        stepManager.value = StepManager.NextStep
    }

    fun prevStep() {
        stepManager.value = StepManager.PreviousStep
    }

    fun startWorkout() {
        running.value = true
        stepManager.value = StepManager.Start
    }

    fun pauseWorkout() {
        if (running.value == true) {
            stepManager.value = StepManager.Pause
            running.value = false
        }
        else {
            stepManager.value = StepManager.Start
            running.value = true
        }
    }

    fun stopWorkout() {
        running.value = false
        active.value = false
        stepManager.value = StepManager.Stop
    }

    fun navigateToHome() {
        navigation.value = HomeNavigation.WorkoutToHome
    }

    fun combineLists(): List<String> {
        return steps.zip(durations) { step, duration ->
            "$step $duration"
        }
    }

    fun displayError() {
        errorMessage.value = globalErrorMessage
    }

    fun stepChanged() {
        currentItemIndex.value = currentItemIndex.value?.plus(1)
    }

    companion object {
        const val globalErrorMessage = "Oops. An error occurred!"
    }
}






