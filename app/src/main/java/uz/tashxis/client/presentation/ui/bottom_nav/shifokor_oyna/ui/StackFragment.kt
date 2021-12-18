package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber
import uz.tashxis.client.R
import uz.tashxis.client.business.util.NetworkStatus
import uz.tashxis.client.business.util.lazyFast
import uz.tashxis.client.data.RetrofitClient
import uz.tashxis.client.databinding.FragmentStackBinding
import uz.tashxis.client.databinding.LayoutDialogBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.framework.repo.MainRepository
import uz.tashxis.client.framework.viewModel.StackViewModel
import uz.tashxis.client.framework.viewModel.StackViewModelFactory
import uz.tashxis.client.presentation.adapters.StackDaysAdapter
import uz.tashxis.client.presentation.adapters.StackDaysClickListener
import uz.tashxis.client.presentation.adapters.StackTimesAdapter
import uz.tashxis.client.presentation.adapters.StackTimesClickListener
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.PassQueueData
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResLocal
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.StackDaysData


class StackFragment : BaseFragment<FragmentStackBinding>(FragmentStackBinding::inflate),
    StackDaysClickListener,
    StackTimesClickListener {
    private val bundle by lazyFast { Bundle() }
    private val reportDialog by lazy {
        context?.let {
            MaterialAlertDialogBuilder(it, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
                .setView(R.layout.layout_dialog)
                .setCancelable(false)
                .create()
        }
    }

    private var _layoutDialogBinding: LayoutDialogBinding? = null
    private val layoutDialogBinding get() = _layoutDialogBinding!!
    private val passQueueData by lazyFast { PassQueueData() }
    private lateinit var viewModel: StackViewModel
    private val stackDaysAdapter: StackDaysAdapter by lazy { StackDaysAdapter() }
    private val stackTimesAdapter: StackTimesAdapter by lazy { StackTimesAdapter() }
    private var doctorId: Int = 0
    private var price: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            doctorId = it.getInt("id")
            price = it.getInt("price")
        }
        val api = RetrofitClient.instance
        viewModel = ViewModelProvider(
            requireActivity(),
            StackViewModelFactory(requireActivity().application, MainRepository(api))
        )[StackViewModel::class.java]
        viewModel.getStackDays(doctorId)
        viewModel.getStackTimes(doctorId)
    }

    override fun viewCreated() {
        super.viewCreated()
        setUpViews()
        setupObserver()
    }

    private val stackDaysObserver = Observer<NetworkStatus<List<StackDaysData>>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                showProgress()
                Timber.d("Loading: $it")
            }
            is NetworkStatus.SUCCESS -> {
                hideProgress()
                Timber.d("Succes: " + it.data)
                stackDaysAdapter.submitList(it.data)
                //binding.toolbarDoctors.title = it.data[0].speciality!!.name.toString()
            }
            is NetworkStatus.ERROR -> {
                hideProgress()
                Timber.d("Error: " + it.error)
            }
        }
    }
    private val stackTimesObserver = Observer<NetworkStatus<List<String>>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                showProgress()
                Timber.d("Loading: $it")
            }
            is NetworkStatus.SUCCESS -> {
                hideProgress()
                Timber.d("Succes: " + it.data)
                stackTimesAdapter.submitList(it.data)
            }
            is NetworkStatus.ERROR -> {
                hideProgress()
                Timber.d("Error: " + it.error)

            }
        }
    }
    private val stackCommitObserver = Observer<NetworkStatus<AddQueueResLocal>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                showProgress()
            }
            is NetworkStatus.SUCCESS -> {
                hideProgress()
                setPassQuery(it)
                showReport()
            }
            is NetworkStatus.ERROR -> {
                Timber.d("stackcommitfragment: " + it.error)
                hideProgress()
            }
        }
    }

    private fun setPassQuery(it: NetworkStatus.SUCCESS<AddQueueResLocal>) {
        passQueueData.clientId = it.data.id
        passQueueData.date = it.data.date
    }

    private fun setupObserver() {
        viewModel.liveStackDayState.observe(viewLifecycleOwner, stackDaysObserver)
        viewModel.livestackTimeState.observe(viewLifecycleOwner, stackTimesObserver)
        viewModel.liveStackCommit.observe(viewLifecycleOwner, stackCommitObserver)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViews() {
        binding.tvFio.text =
            """ ${preferences.surename.toString()}  ${preferences.name.toString()}  ${preferences.fathername.toString()}"""
        binding.tvNumber.text = preferences.phone
        binding.tvSex.text = preferences.gender
        binding.tvAge.text = "22"
        stackTimesAdapter.itemClickListener(this)
        stackDaysAdapter.itemClickListener(this)
        binding.rvStackDate.adapter = stackDaysAdapter
        binding.rvStackTime.adapter = stackTimesAdapter

        binding.btnNavbat.setOnClickListener {
            stackCommit()
        }
    }

    private var selectedDatePos = -1
    override fun onDateClicked(model: StackDaysData, position: Int) {
        if (selectedDatePos != -1)
            stackDaysAdapter.notifyItemChanged(selectedDatePos, StackDaysAdapter.UNSELECTED_DATE)
        stackDaysAdapter.notifyItemChanged(position, StackDaysAdapter.SELECTED_DATE)
        selectedDatePos = position
        viewModel.date = model.date
    }

    private var selectedTimePos = -1
    override fun onTimeClicked(model: String, position: Int) {
        if (selectedTimePos != -1)
            stackTimesAdapter.notifyItemChanged(selectedTimePos, StackTimesAdapter.UNSELECTED_TIME)
        stackTimesAdapter.notifyItemChanged(position, StackTimesAdapter.SELECTED_TIME)
        selectedTimePos = position
        viewModel.time = model
    }

    private fun stackCommit() {
        viewModel.stackCommit(id = doctorId, price = price)
    }

    @SuppressLint("SetTextI18n")
    private fun showReport() {
        reportDialog?.show()
        layoutDialogBinding.dialogText.text = "${preferences.firebaseToken}\"pa\n" +
                "siz ${passQueueData.date} kuni 9:${passQueueData.time} ga\n" +
                "navbatga yozildingiz.\n" +
                "Shifoxonaga 30 minut oldin\n" +
                "borishingizni tavsiya qilamiz.\""
        layoutDialogBinding.btnOk.setOnClickListener{
            bundle.putParcelable("queueData", passQueueData)
            findNavController().navigate(R.id.action_stackFragment_to_boshFragment,bundle)
        }
    }


}