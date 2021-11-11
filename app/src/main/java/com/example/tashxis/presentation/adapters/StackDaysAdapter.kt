package com.example.tashxis.presentation.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tashxis.App
import com.example.tashxis.R
import com.example.tashxis.databinding.ItemStackDateBinding
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.StackDaysData

class StackDaysAdapter :
    ListAdapter<StackDaysData, StackDaysAdapter.StackDaysViewHolder>(StackDaysDiffer()) {

    private var clickListener: StackDaysClickListener? = null
    private var selectedDatePos = -1

    fun itemClickListener(clickListener: StackDaysClickListener) {
        this.clickListener = clickListener
    }

    class StackDaysViewHolder(
        private val binding: ItemStackDateBinding,
        private val clickListener: StackDaysClickListener?

    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var model: StackDaysData? = null

        fun onBind(model: StackDaysData) {
            binding.root.setOnClickListener(this)
            this.model = model
            binding.tvDateNumber.text = model.date.take(2)
            binding.tvDateWeek.text = model.weekday
            if (model.selected) onSelect()
            else onUnSelect()
        }

        override fun onClick(p0: View?) {
            model?.let { clickListener?.onDateClicked(it, adapterPosition) }
        }

        fun onSelect() {
            App.context?.let {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(it, R.color.checkColor))
            }
            binding.tvDateWeek.setTextColor(Color.WHITE)
            binding.tvDateNumber.setTextColor(Color.WHITE)

        }

        fun onUnSelect() {
            binding.root.setCardBackgroundColor(Color.WHITE)
            binding.tvDateWeek.setTextColor(Color.BLACK)
            binding.tvDateNumber.setTextColor(Color.BLACK)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackDaysViewHolder {
        val binding =
            ItemStackDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return StackDaysViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(
        holder: StackDaysViewHolder,
        position: Int
    ) {
        holder.onBind(currentList[position])
    }

    override fun onBindViewHolder(
        holder: StackDaysViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.d("TAGTAG", "onBindViewHolder: $payloads --- $position")
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        when (payloads.last()) {
            SELECTED_DATE -> {
                if (selectedDatePos != -1) {
                    currentList[selectedDatePos].selected = false
                }
                currentList[position].selected = true
                holder.onSelect()
                selectedDatePos = holder.adapterPosition
            }
            UNSELECTED_DATE -> {
                currentList[position].selected = false
                holder.onUnSelect()
            }
            else -> {
                super.onBindViewHolder(holder, position, payloads)
            }
        }

    }

    companion object {
        const val SELECTED_DATE = "selected_date"
        const val UNSELECTED_DATE = "unselected_date"
    }

}

class StackDaysDiffer : DiffUtil.ItemCallback<StackDaysData>() {

    override fun areItemsTheSame(oldItem: StackDaysData, newItem: StackDaysData): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: StackDaysData, newItem: StackDaysData): Boolean {
        return oldItem.equals(newItem)
    }

}

interface StackDaysClickListener {
    fun onDateClicked(model: StackDaysData, position: Int)
}