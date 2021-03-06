package kg.bakai.sunrisejokes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.bakai.sunrisejokes.databinding.ItemJokeBinding
import kg.bakai.sunrisejokes.domain.model.Joke

class Adapter : ListAdapter<Joke, Adapter.JokeViewHolder>(DiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJokeBinding.inflate(inflater, parent, false)

        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JokeViewHolder(private val binding: ItemJokeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Joke) {
            with(binding) {
                setup.text = item.setup
                punchline.text = item.punchline
            }
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem == newItem
        }
    }
}