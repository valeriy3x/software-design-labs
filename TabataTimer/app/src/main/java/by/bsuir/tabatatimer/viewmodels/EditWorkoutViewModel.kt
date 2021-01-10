package by.bsuir.tabatatimer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.TabataTimerApplication
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.repositories.Repository
import by.bsuir.tabatatimer.utilities.HomeNavigation
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditWorkoutViewModel(private val repo: Repository) : ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData("")
    val color: MutableLiveData<Int> = MutableLiveData()
    val prepareTime: MutableLiveData<Int> = MutableLiveData(25)
    val workTime: MutableLiveData<Int> = MutableLiveData(45)
    val restTime: MutableLiveData<Int> = MutableLiveData(10)
    val cycles: MutableLiveData<Int> = MutableLiveData(1)
    val setsCount: MutableLiveData<Int> = MutableLiveData(1)
    val restBetweenSets: MutableLiveData<Int> = MutableLiveData(0)
    val coolDown: MutableLiveData<Int> = MutableLiveData(0)
    val navigation: SingleLiveEvent<HomeNavigation> = SingleLiveEvent()
    val warningMessage: SingleLiveEvent<String> = SingleLiveEvent()
    private val compositeDisposable = CompositeDisposable()
    private var id: Int? = null

    fun createOrUpdateWorkout() {
        val sequence = Sequence(
            title = title.value,
            color = color.value,
            prepareTime = prepareTime.value,
            workTime = workTime.value,
            restTime = restTime.value,
            cycles = cycles.value,
            setsCount = setsCount.value,
            restBetweenSets = restBetweenSets.value,
            coolDown = coolDown.value, id = null
        )

        if (id == null) {
            compositeDisposable.add(Completable.fromAction {
                repo.insertSequence(sequence)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    navigation.value = HomeNavigation.EditToHome
                }, {

                }))
        } else {
            sequence.id = id

            compositeDisposable.add(Completable.fromAction {
                repo.updateSequence(sequence)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    navigation.value = HomeNavigation.EditToHome
                }, {

                }))
        }
    }

    fun fillFields(sequence: Sequence) {
        id = sequence.id
        title.value = sequence.title
        color.value = sequence.color
        prepareTime.value = sequence.prepareTime
        workTime.value = sequence.workTime
        restTime.value = sequence.restTime
        cycles.value = sequence.cycles
        setsCount.value = sequence.setsCount
        restBetweenSets.value = sequence.restBetweenSets
        coolDown.value = sequence.coolDown
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun increasePrepareTime() {
        prepareTime.value = prepareTime.value?.plus(1)
    }

    fun decreasePrepareTime() {
        if (prepareTime.value == 0)
            warningMessage.value = minValReachedMessage
        else
            prepareTime.value = prepareTime.value?.minus(1)
    }

    fun increaseWorkTime() {
        workTime.value = workTime.value?.plus(1)
    }

    fun decreaseWorkTime() {
        if (workTime.value == 1)
            warningMessage.value = minValReachedMessage
        else
            workTime.value = workTime.value?.minus(1)
    }

    fun increaseRestTime() {
        restTime.value = restTime.value?.plus(1)
    }

    fun decreaseRestTime() {
        if (restTime.value == 0)
            warningMessage.value = minValReachedMessage
        else
            restTime.value = restTime.value?.minus(1)
    }

    fun increaseCycles() {
        cycles.value = cycles.value?.plus(1)
    }

    fun decreaseCycles() {
        if (cycles.value == 1)
            warningMessage.value = minValReachedMessage
        else
            cycles.value = cycles.value?.minus(1)
    }

    fun increaseSetsCount() {
        setsCount.value = setsCount.value?.plus(1)
    }

    fun decreaseSetsCount() {
        if (setsCount.value == 1)
            warningMessage.value = minValReachedMessage
        else
            setsCount.value = setsCount.value?.minus(1)
    }

    fun increaseRestBetweenSets() {
        restBetweenSets.value = restBetweenSets.value?.plus(1)
    }

    fun decreaseRestBetweenSets() {
        if (restBetweenSets.value == 0)
            warningMessage.value = minValReachedMessage
        else
            restBetweenSets.value = restBetweenSets.value?.minus(1)
    }

    fun increaseCoolDown() {
        coolDown.value = coolDown.value?.plus(1)
    }

    fun decreaseCoolDown() {
        if (coolDown.value == 0)
            warningMessage.value = minValReachedMessage
        else
            coolDown.value = coolDown.value?.minus(1)
    }

    companion object {
        private val minValReachedMessage = TabataTimerApplication.applicationContext?.getString(R.string.min_val_error)
    }
}