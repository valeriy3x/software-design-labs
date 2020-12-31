package by.bsuir.tabatatimer.viewmodels

import android.graphics.Color
import android.util.Log
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.repositories.Repository
import by.bsuir.tabatatimer.utilities.HomeNavigation
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repo: Repository): ViewModel(){
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val dataSet = MutableLiveData<List<Sequence>>()
    val navigation: SingleLiveEvent<HomeNavigation> = SingleLiveEvent()

    init {
        fetchData()
    }

    fun fetchData() {
        compositeDisposable.add(
            repo.getSequences()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dataSet.value = it
            },
                {
                    Log.d("Tag", it.message.toString())
                }))
    }

    fun navigateToEdit() {
        navigation.value = HomeNavigation.HomeToEdit
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}