package by.bsuir.tabatatimer.repositories

import by.bsuir.tabatatimer.TabataTimerApplication
import by.bsuir.tabatatimer.data.dbo.SequenceDbo
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.database.SequencesDao
import io.reactivex.Flowable

object RepositoryImpl: Repository {
    private val sequencesDao: SequencesDao = TabataTimerApplication.database.provideDao()

    override fun getSequences(): Flowable<List<Sequence>> {
        return sequencesDao.getSequences()
            .flatMap {
                Flowable.just(it.map { element -> element.toSequence() }
                    .toList())
            }
    }

    override fun insertSequence(sequence: Sequence) {
        sequencesDao.insert(sequence.toSequenceDbo())
    }

    override fun deleteSequence(sequence: Sequence) {
        sequencesDao.delete(sequence.toSequenceDbo())
    }

    override fun updateSequence(sequence: Sequence) {
        sequencesDao.update(sequence.toSequenceDbo())
    }

    override fun deleteAllSequences() {
        TabataTimerApplication.database.clearAllTables()
    }

    private fun SequenceDbo.toSequence() = Sequence(
        id, title, color, prepareTime, workTime, restTime, cycles, setsCount, restBetweenSets, coolDown
    )

    private fun Sequence.toSequenceDbo() = SequenceDbo(
        id, title, color, prepareTime, workTime, restTime, cycles, setsCount, restBetweenSets, coolDown
    )
}