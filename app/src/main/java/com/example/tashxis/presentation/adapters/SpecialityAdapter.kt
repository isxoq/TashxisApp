package com.example.tashxis.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tashxis.App
import com.example.tashxis.R
import com.example.tashxis.business.util.Constants
import com.example.tashxis.databinding.ItemSpecialityBinding
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.speciality.SpecialData

class SpecialityAdapter :
    ListAdapter<SpecialData, SpecialityAdapter.SpecialityVH>(ItemDiffer()) {
    private val TAG = "TAG"
    private var clickListener: SpecialityClickListener? = null
    fun itemClickListener(clickListener: SpecialityClickListener) {
        this.clickListener = clickListener
    }

    class SpecialityVH(
        private val binding: ItemSpecialityBinding,
        private val clickListener: SpecialityClickListener?
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var model: SpecialData? = null
        fun onBind(model: SpecialData) {
            binding.root.setOnClickListener(this)
            this.model = model
            Glide
                .with(App.context!!)
                .load(Constants.BASE_URL + model.logoUrl)
                .placeholder(R.drawable.ic_spec)
                .centerCrop()
                .into(binding.ivSpeciality)
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

class ItemDiffer : DiffUtil.ItemCallback<SpecialData>() {
    override fun areItemsTheSame(oldItem: SpecialData, newItem: SpecialData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SpecialData, newItem: SpecialData): Boolean {
        return oldItem.id == newItem.id
    }

}

interface SpecialityClickListener {
    fun onClicked(model: SpecialData)
}