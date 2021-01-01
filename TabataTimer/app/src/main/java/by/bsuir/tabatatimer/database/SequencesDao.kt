package by.bsuir.tabatatimer.database

import androidx.room.*
import by.bsuir.tabatatimer.data.dbo.SequenceDbo
import io.reactivex.Flowable

@Dao
interface SequencesDao {

    @Query("SELECT * FROM sequences")
    fun getSequences(): Flowable<List<SequenceDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sequence: SequenceDbo)

    @Update
    fun update(sequence: SequenceDbo)

    @Delete
    fun delete(sequence: SequenceDbo)

}