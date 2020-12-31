package by.bsuir.tabatatimer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.bsuir.tabatatimer.data.dbo.SequenceDbo
import io.reactivex.Flowable

@Dao
interface SequencesDao {

    @Query("SELECT * FROM sequences")
    fun getSequences(): Flowable<List<SequenceDbo>>

    @Insert
    fun insertAll(sequence: SequenceDbo)
}