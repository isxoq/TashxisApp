package uz.tashxis.client.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import uz.tashxis.client.R
import uz.tashxis.client.databinding.ItemQueryBinding
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.PassQueueData
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.Queue

class QueryAdapter(passQueue: PassQueueData?, options: FirebaseRecyclerOptions<Queue>) :
    FirebaseRecyclerAdapter<Queue, QueryAdapter.QueueViewHolder>(options) {
    private var clickListener: ((model: Queue) -> Unit)? = null
    private var isActivated = false
    private var isMyself = false
    private val passedData = passQueue
    fun setClickListener(clickListener: (model: Queue) -> Unit) {
        this.clickListener = clickListener
    }

    class QueueViewHolder(
        private val binding: ItemQueryBinding,
        private val clickListener: ((model: Queue) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(queue: Queue, isActivated: Boolean, isMyself: Boolean) = with(binding) {
            if (isActivated) {
                if (isMyself) {
                    binding.clQueue.setBackgroundColor(R.color.black)
                    binding.root.setOnClickListener { clickListener?.invoke(queue) }
                }
                tvNumberQueue.text = "${queue.queueNumber}"
                tvTime.text = queue.time
            }
          /* else{
                binding.root.visibility = View.INVISIBLE
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
        val binding = ItemQueryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return QueueViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: QueueViewHolder, position: Int, model: Queue) {
        if (model.doctor?.id == passedData?.doctorId){
            isActivated = true
            isMyself = model.clientId == passedData?.clientId
            holder.bind(model, isActivated, isMyself)
        }
        else{
            isActivated = false
            holder.bind(model, isActivated, isMyself)
        }

    }

}
