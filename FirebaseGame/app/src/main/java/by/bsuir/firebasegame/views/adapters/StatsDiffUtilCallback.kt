package by.bsuir.firebasegame.views.adapters

import androidx.recyclerview.widget.DiffUtil
import by.bsuir.firebasegame.data.viewdata.Stat

class StatsDiffUtilCallback: DiffUtil.ItemCallback<Stat>() {
    override fun areItemsTheSame(oldItem: Stat, newItem: Stat): Boolean {
        return oldItem.gameId == newItem.gameId
    }

    override fun areContentsTheSame(oldItem: Stat, newItem: Stat): Boolean {
        return oldItem.copy(gameId = newItem.gameId) == newItem
    }
}