package uz.tashxis.client.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.tashxis.client.R
import uz.tashxis.client.databinding.ItemStackTimeBinding

class StackTimesAdapter :
    ListAdapter<String, StackTimesAdapter.StackTimesViewHolder>(StackTimesItemDifference()) {

    private var clickListener: StackTimesClickListener? = null
    private var selectedTimePos = -1

    fun itemClickListener(clickListener: StackTimesClickListener) {
        this.clickListener = clickListener
    }

    class StackTimesViewHolder(
        private val binding: ItemStackTimeBinding,
        private val clickListener: StackTimesClickListener?
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var model: String? = null

        fun onBind(model: String) {
            binding.root.setOnClickListener(this)
            binding.rvStackTime.text = model
            this.model = model
        }

        override fun onClick(p0: View?) {
            model?.let { clickListener?.onTimeClicked(it, adapterPosition) }
        }

        fun onSelect() {
            binding.root.setCardBackgroundColor(
                ContextCompat.getColor(
                    uz.tashxis.client.App.context!!,
                    R.color.checkColor
                )
            )
        }

        fun onUnselect() {
            binding.root.setCardBackgroundColor(Color.WHITE)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StackTimesViewHolder {
        val binding = ItemStackTimeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StackTimesViewHolder(binding, clickListener)

    }

    override fun onBindViewHolder(holder: StackTimesViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun onBindViewHolder(
        holder: StackTimesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        when (payloads.last()) {
            SELECTED_TIME -> {
//                if (selectedTimePos != -1)
//                    currentList[selectedTimePos].selected = false
                holder.onSelect()
            }
            UNSELECTED_TIME -> {
                holder.onUnselect()
            }
            else -> {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    companion object {
        const val SELECTED_TIME = "selected_time"
        const val UNSELECTED_TIME = "unselected_time"
    }
}

class StackTimesItemDifference : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem.equals(newItem)
    }

}

interface StackTimesClickListener {
    fun onTimeClicked(model: String, position: Int)
}
