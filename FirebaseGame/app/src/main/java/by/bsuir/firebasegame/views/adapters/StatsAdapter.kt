package by.bsuir.firebasegame.views.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.firebasegame.data.viewdata.Stat
import by.bsuir.firebasegame.databinding.ItemStatBinding

class StatsAdapter: ListAdapter<Stat, StatsAdapter.StatsViewHolder>(StatsDiffUtilCallback()) {
    class StatsViewHolder(private val binding: ItemStatBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(stat: Stat) {
            binding.stat = stat
            if(stat.won)
                binding.status.setTextColor(Color.GREEN)
            else
                binding.status.setTextColor(Color.RED)

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemStatBinding.inflate(layoutInflater, parent, false)

        return StatsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}