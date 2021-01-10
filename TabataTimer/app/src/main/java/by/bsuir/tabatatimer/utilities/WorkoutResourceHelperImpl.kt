package by.bsuir.tabatatimer.utilities

import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.TabataTimerApplication

object WorkoutResourceHelperImpl : WorkoutResourceHelper {
    private val applicationContext = TabataTimerApplication.applicationContext

    override val prepareString: String?
        get() = applicationContext?.resources?.getString(R.string.prepare)

    override val workString: String?
        get() = applicationContext?.getString(R.string.work)

    override val restString: String?
        get() = applicationContext?.getString(R.string.rest)

    override val restBetweenSetsString: String?
        get() = applicationContext?.getString(R.string.rest_between_sets)

    override val coolDownString: String?
        get() = applicationContext?.getString(R.string.cool_down)

}