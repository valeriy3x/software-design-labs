package by.bsuir.tabatatimer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.bsuir.tabatatimer.data.dbo.SequenceDbo

@Database(entities = [SequenceDbo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun provideDao(): SequencesDao
}