package by.bsuir.tabatatimer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.TabataTimerApplication
import by.bsuir.tabatatimer.repositories.Repository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SettingsViewModel(private val repo: Repository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val message: MutableLiveData<String> = MutableLiveData()


    fun clearData() {
        compositeDisposable.add(
            Completable.fromAction {
                repo.deleteAllSequences()
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    message.value = successMessage
                }
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private val successMessage = TabataTimerApplication.applicationContext?.getString(R.string.success_delete)
    }
}