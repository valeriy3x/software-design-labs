package by.bsuir.tabatatimer.repositories

import io.reactivex.Flowable
import by.bsuir.tabatatimer.data.viewdata.Sequence
import io.reactivex.Observable
import io.reactivex.Single

interface Repository {
    fun getSequences(): Flowable<List<Sequence>>
    fun insertSequence(sequence: Sequence)
    fun deleteSequence(sequence: Sequence)
    fun updateSequence(sequence: Sequence)
}