package uz.tashxis.client.presentation.ui.bottom_nav.navbat_oyna

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.navigation.findNavController
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import uz.tashxis.client.R
import uz.tashxis.client.business.util.MarginTopBottomItemDecoration
import uz.tashxis.client.business.util.lazyFast
import uz.tashxis.client.databinding.FragmentNavbatBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.presentation.adapters.QueryAdapter
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.PassQueueData
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.Queue

class QueueFragment : BaseFragment<FragmentNavbatBinding>(FragmentNavbatBinding::inflate) {
    private var passQueue: PassQueueData? = null
    private lateinit var options: FirebaseRecyclerOptions<Queue>
    private val bundle1 by lazyFast { Bundle() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            passQueue = bundle.getParcelable<Parcelable>("queueData") as PassQueueData?
        }
        val query: Query by lazy {
            FirebaseDatabase.getInstance()
                .getReference("queues")
                .orderByChild("date")
                .equalTo(passQueue?.date)
        }
        val options: FirebaseRecyclerOptions<Queue> by lazy {
            FirebaseRecyclerOptions.Builder<Queue>()
                .setQuery(query, Queue::class.java)
                .setLifecycleOwner(this)
                .build()
        }
        this.options = options
    }

    private val queueAdapter: QueryAdapter by lazy { QueryAdapter(passQueue, options) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setupRecycler()
    }

    private fun setUpViews() {
        binding.tvDoctorName.text = passQueue?.doctorName ?: "Unknown"
        binding.tvDoctorSpeciality.text = passQueue?.speciality ?: "Unknown"
        binding.tvDate.text = passQueue?.date
        binding.tvTime.text = passQueue?.time
        binding.tvPrice.text = "Unknown"
    }

    private fun setupRecycler() {
        binding.rvQueue.apply {
            addItemDecoration(MarginTopBottomItemDecoration(40))
            adapter = queueAdapter.apply {
                setClickListener { queue ->
                    bundle1.putParcelable("queueData", passQueue)
                    findNavController().navigate(R.id.action_navbatFragment_to_queueInfoFragment, bundle1)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        queueAdapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        queueAdapter.stopListening()
    }

}