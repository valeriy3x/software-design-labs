package by.bsuir.tabatatimer.data.viewdata

data class Sequence(
    val id: Int,
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