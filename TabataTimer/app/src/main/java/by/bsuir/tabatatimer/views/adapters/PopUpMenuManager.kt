package by.bsuir.tabatatimer.views.adapters

import android.view.View
import by.bsuir.tabatatimer.data.viewdata.Sequence

interface PopUpMenuManager<T> {
    fun showPopUp(view: View, model: T)
}