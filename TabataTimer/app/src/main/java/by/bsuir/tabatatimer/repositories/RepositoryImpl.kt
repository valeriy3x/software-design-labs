package by.bsuir.tabatatimer.repositories

import androidx.room.Room
import by.bsuir.tabatatimer.TabataTimerApplication
import by.bsuir.tabatatimer.data.dbo.SequenceDbo
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.database.AppDatabase
import by.bsuir.tabatatimer.database.SequencesDao
import io.reactivex.Flowable

object RepositoryImpl: Repository {
    val sequencesDao: SequencesDao = Room.databaseBuilder(TabataTimerApplication.applicationContext!!, AppDatabase::class.java, "tabata-base").build().provideDao()

    override fun getSequences(): Flowable<List<Sequence>> {
        return sequencesDao.getSequences()
            .flatMap {
                Flowable.just(it.map { element -> element.toSequence() }
                    .toList())
            }
    }

    override fun insertAll(sequences: List<Sequence>) {
        sequencesDao.insertAll(sequences[0].toSequenceDbo())
    }

    private fun SequenceDbo.toSequence() = Sequence(
        id, title, color, prepareTime, workTime, restTime, cycles, setsCount, restBetweenSets, coolDown
    )

    private fun Sequence.toSequenceDbo() = SequenceDbo(
        id, title, color, prepareTime, workTime, restTime, cycles, setsCount, restBetweenSets, coolDown
    )
}