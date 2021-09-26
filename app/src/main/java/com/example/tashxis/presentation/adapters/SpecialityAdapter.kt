package com.example.tashxis.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tashxis.R
import com.example.tashxis.databinding.ItemSpecialityBinding
import com.example.tashxis.presentation.ui.auth.model.main.SpecialityData

class SpecialityAdapter :
    ListAdapter<SpecialityData, SpecialityAdapter.SpecialityVH>(ItemDiffer()) {
    private var clickListener: SpecialityClickListener? = null
    fun itemClickListener(clickListener: SpecialityClickListener) {
        this.clickListener = clickListener
    }

    class SpecialityVH(
        private val binding: ItemSpecialityBinding,
        private val clickListener: SpecialityClickListener?
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var model: SpecialityData? = null
        fun onBind(model: SpecialityData) {
            binding.root.setOnClickListener(this)
            this.model = model
            binding.tvSpecialityName.text = model.name
            binding.tvSpecialityDesc.text = model.description
            binding.tvCount.text =
                itemView.context.getString(R.string.spec_count_string, model.doctorsCount)
        }

        override fun onClick(p0: View?) {
            clickListener?.onClicked(model!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityVH {
        val binding =
            ItemSpecialityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialityVH(binding, clickListener)
    }

    override fun onBindViewHolder(holder: SpecialityVH, position: Int) {
        holder.onBind(currentList[position])
    }
}

class ItemDiffer : DiffUtil.ItemCallback<SpecialityData>() {
    override fun areItemsTheSame(oldItem: SpecialityData, newItem: SpecialityData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SpecialityData, newItem: SpecialityData): Boolean {
        return oldItem.id == newItem.id
    }

}

interface SpecialityClickListener {
    fun onClicked(model: SpecialityData)
}