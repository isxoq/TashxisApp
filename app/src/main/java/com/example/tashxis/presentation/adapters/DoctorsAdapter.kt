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
import com.example.tashxis.databinding.ItemDoctorsLayoutBinding
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response.DoctorResponseData

class DoctorsAdapter :
    ListAdapter<DoctorResponseData, DoctorsAdapter.DoctorsVH>(ItemDifference()) {
    private var clickListener: DoctorsClickListener? = null
        fun itemClickListener(clickListener: DoctorsClickListener) {
        this.clickListener = clickListener
    }

    class DoctorsVH(
        private val binding: ItemDoctorsLayoutBinding,
        private val clickListener: DoctorsClickListener?
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var model: DoctorResponseData? = null
        fun onBind(model: DoctorResponseData) {
            binding.root.setOnClickListener(this)
            this.model = model
            Glide
                .with(App.context!!)
                .load(Constants.BASE_URL+model.imageUrl)
                .placeholder(R.drawable.ic_doctor)
                .centerCrop()
                .into(binding.ivDoctor)

            binding.tvDoctorName.text = "DR. ${model.firstName}"
            binding.tvDoctorSpeciality.text = model.speciality!!.name
            binding.tvQabulPrice.text = itemView.context.getString(R.string.price,model.acceptanceAmount.toString())
            binding.tvDistanceDoctor.text = itemView.context.getString(R.string.distance,model.distance.toString())
            binding.tvStarCount.text = model.rate.toString()
            binding.tvCommentDoctor.text = itemView.context.getString(R.string.string_comment,model.id.toString())
            // binding.tvCount.text =
            // itemView.context.getString(R.string.spec_count_string, model.doctorsCount)
        }

        override fun onClick(p0: View?) {
            clickListener?.onClicked(model!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsVH {
        val binding =
            ItemDoctorsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorsVH(binding, clickListener)
    }

    override fun onBindViewHolder(holder: DoctorsVH, position: Int) {
        holder.onBind(currentList[position])
    }
}

class ItemDifference : DiffUtil.ItemCallback<DoctorResponseData>() {
    override fun areItemsTheSame(
        oldItem: DoctorResponseData,
        newItem: DoctorResponseData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: DoctorResponseData,
        newItem: DoctorResponseData
    ): Boolean {
        return oldItem.id == newItem.id
    }

}

interface DoctorsClickListener {
    fun onClicked(model: DoctorResponseData)
}