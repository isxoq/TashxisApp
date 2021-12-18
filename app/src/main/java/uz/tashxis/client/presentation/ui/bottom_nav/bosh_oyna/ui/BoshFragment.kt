package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import timber.log.Timber
import uz.tashxis.client.R
import uz.tashxis.client.business.util.GridSpacingItemDecoration
import uz.tashxis.client.business.util.MarginTopBottomItemDecoration
import uz.tashxis.client.business.util.NetworkStatus
import uz.tashxis.client.business.util.lazyFast
import uz.tashxis.client.data.RetrofitClient
import uz.tashxis.client.databinding.FragmentBoshBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.framework.repo.MainRepository
import uz.tashxis.client.framework.viewModel.GetNearDoctorsVM
import uz.tashxis.client.framework.viewModel.GetNearDoctorsVMF
import uz.tashxis.client.presentation.adapters.NearDoctorsAdapter
import uz.tashxis.client.presentation.adapters.NearDoctorsClickListener
import uz.tashxis.client.presentation.adapters.QueueAdapter
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor.GetNearDoctorsRes
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.PassQueueData
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.Queue


class BoshFragment : BaseFragment<FragmentBoshBinding>(FragmentBoshBinding::inflate),
    NearDoctorsClickListener {

    private var passQueueData: PassQueueData? = null
    private val bundle by lazyFast { Bundle() }
    private var query: Query? = null
    private var options: FirebaseRecyclerOptions<Queue>? = null
    private var queueAdapter: QueueAdapter? = null
    private val nearDoctorsAdapter: NearDoctorsAdapter by lazy { NearDoctorsAdapter() }
    private lateinit var viewModel: GetNearDoctorsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = RetrofitClient.instance
        val inComeBundle = this.arguments
        if (inComeBundle != null) {
            passQueueData = inComeBundle.getParcelable<Parcelable>("queue") as PassQueueData
        }
        query = FirebaseDatabase.getInstance().getReference("queues")
            .orderByChild(passQueueData?.clientId.toString())
        options = FirebaseRecyclerOptions.Builder<Queue>()
            .setQuery(query!!, Queue::class.java)
            .setLifecycleOwner(this)
            .build()
        viewModel = ViewModelProvider(
            requireActivity(),
            GetNearDoctorsVMF(requireActivity().application, MainRepository(api))
        )[GetNearDoctorsVM::class.java]
        viewModel.getNearDoctors()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvIsm.text = preferences.name
        setUpViews()
        setupRecycler()
        // fetchLocation()
        setUpObserver()
    }

    private fun setUpViews() {
        queueAdapter = QueueAdapter(options!!)
        Timber.d("setUpViews: ")
        binding.rvNearDoctor.adapter = nearDoctorsAdapter
        nearDoctorsAdapter.itemClickListener(this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvNearDoctor.layoutManager = layoutManager
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen._5sdp)
        binding.rvNearDoctor.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                spacingInPixels,
                true,
                0
            )
        )
    }

    //firebase adapter
    private fun setupRecycler() {
        binding.rvWaiting.apply {
            addItemDecoration(MarginTopBottomItemDecoration(40))
            adapter = queueAdapter?.apply {
                setClickListener { queue ->
                    //TODO
                }
            }
        }
    }

    private fun setUpObserver() {
        viewModel._liveState.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkStatus.LOADING -> {
                    showProgress()
                    Timber.d("Loading: $it")
                }
                is NetworkStatus.SUCCESS -> {
                    Timber.d("Success: " + it.data)
                    nearDoctorsAdapter.submitList(it.data)
                    hideProgress()
                    //binding.toolbarDoctors.title = it.data[0].speciality!!.name.toString()
                }
                is NetworkStatus.ERROR -> {
                    hideProgress()
                    Timber.d("Error: " + it.error)
                }
            }

        })

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        queueAdapter?.startListening()
        /* queueAdapter?.notifyDataSetChanged()*/
    }

    override fun onStop() {
        super.onStop()
        queueAdapter?.stopListening()
    }

    override fun onClicked(model: GetNearDoctorsRes) {
        val bundle = bundleOf("id" to model.id)
        findNavController().navigate(R.id.action_boshFragment_to_aboutDoctorFragment, bundle)
    }
}
//}