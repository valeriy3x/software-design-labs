package by.bsuir.tabatatimer.data.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sequences")
data class SequenceDbo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val color: Int,
    val prepareTime: Int,
    val workTime: Int,
    val restTime: Int,
    val cycles: Int,
    val setsCount: Int,
    val restBetweenSets: Int,
    val coolDown: Int

)