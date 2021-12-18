package uz.tashxis.client.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor.GetNearDoctorsRes
import uz.tashxis.client.R
import uz.tashxis.client.databinding.ItemLayoutNearDoctorBinding

class NearDoctorsAdapter :
    ListAdapter<GetNearDoctorsRes, NearDoctorsAdapter.NearDoctorsVH>(GetNearDoctorsDifference()) {
    private var clickListener: NearDoctorsClickListener? = null
    fun itemClickListener(clickListener: NearDoctorsClickListener) {
        this.clickListener = clickListener
    }

    class NearDoctorsVH(
        private val binding: ItemLayoutNearDoctorBinding,
        private val clickListener: NearDoctorsClickListener?

    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var model: GetNearDoctorsRes? = null

        @SuppressLint("SetTextI18n")
        fun onBind(model: GetNearDoctorsRes) {
            binding.root.setOnClickListener(this)
            this.model = model
            binding.tvName.text = "${model.firstName} ${model.lastName}"
            binding.tvSpeciality.text = model.speciality?.name

            Glide
                .with(uz.tashxis.client.App.context!!)
                .load(uz.tashxis.client.business.util.Constants.BASE_URL + model.imageUrl)
                .placeholder(R.drawable.ic_spec)
                .centerCrop()
                .into(binding.ivDoctor)
        }

        override fun onClick(p0: View?) {
            clickListener?.onClicked(model!!)
        }
    }


    class GetNearDoctorsDifference : DiffUtil.ItemCallback<GetNearDoctorsRes>() {
        override fun areItemsTheSame(
            oldItem: GetNearDoctorsRes,
            newItem: GetNearDoctorsRes
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GetNearDoctorsRes,
            newItem: GetNearDoctorsRes
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearDoctorsVH {
        val binding =
            ItemLayoutNearDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NearDoctorsVH(binding, clickListener)
    }

    override fun onBindViewHolder(holder: NearDoctorsVH, position: Int) {
        holder.onBind(currentList[position])
    }

}

interface NearDoctorsClickListener {
    fun onClicked(model: GetNearDoctorsRes)
}