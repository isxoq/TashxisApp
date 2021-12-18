package uz.tashxis.client.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import uz.tashxis.client.databinding.ItemQueueBinding
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.Queue

class QueueAdapter(options: FirebaseRecyclerOptions<Queue>) :
    FirebaseRecyclerAdapter<Queue, QueueAdapter.QueueViewHolder>(options) {
    private var clickListener: ((model: Queue) -> Unit)? = null

    fun setClickListener(clickListener: (model: Queue) -> Unit) {
        this.clickListener = clickListener
    }

    class QueueViewHolder(
        private val binding: ItemQueueBinding,
        private val clickListener: ((model: Queue) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(queue: Queue) = with(binding) {
                binding.root.setOnClickListener { clickListener?.invoke(queue) }
            tvDoctorName.text = "${queue.doctor?.firstName} ${queue.doctor?.lastName}"
            tvAddressName.text = queue.doctor?.address
            tvDate.text = queue.date
            tvTime.text = queue.time
            tvDoctorSpeciality.text = queue.doctor?.speciality
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
        val binding = ItemQueueBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return QueueViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: QueueViewHolder, position: Int, model: Queue) {
        holder.bind(model)
    }

}
