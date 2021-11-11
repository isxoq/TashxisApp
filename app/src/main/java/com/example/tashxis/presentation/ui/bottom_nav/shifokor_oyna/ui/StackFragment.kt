package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.R
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.progressDialog
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentStackBinding
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewModel.StackViewModelFactory
import com.example.tashxis.framework.viewmodel.StackViewModel
import com.example.tashxis.presentation.adapters.StackDaysAdapter
import com.example.tashxis.presentation.adapters.StackDaysClickListener
import com.example.tashxis.presentation.adapters.StackTimesAdapter
import com.example.tashxis.presentation.adapters.StackTimesClickListener
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResLocal
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.StackDaysData


class StackFragment : Fragment(R.layout.fragment_stack), StackDaysClickListener,
    StackTimesClickListener {
    private var _binding: FragmentStackBinding? = null
    private val binding get() = _binding!!
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStackBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setupObserver()
    }

    private val stackDaysObserver = Observer<NetworkStatus<List<StackDaysData>>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                progressDialog?.show()
                Log.d("NetworkStatus", "Loading: ${it}")
            }
            is NetworkStatus.SUCCESS -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Succes: ${it.data}")
                stackDaysAdapter.submitList(it.data)
                //binding.toolbarDoctors.title = it.data[0].speciality!!.name.toString()
            }
            is NetworkStatus.ERROR -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Error: ${it.error}")
            }
        }
    }
    private val stackTimesObserver = Observer<NetworkStatus<List<String>>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                progressDialog?.show()
                Log.d("NetworkStatus", "Loading: ${it}")
            }
            is NetworkStatus.SUCCESS -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Succes: ${it.data}")
                stackTimesAdapter.submitList(it.data)
            }
            is NetworkStatus.ERROR -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Error: ${it.error}")

            }
        }
    }
    private val stackCommitObserver = Observer<NetworkStatus<AddQueueResLocal>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                progressDialog?.show()
            }
            is NetworkStatus.SUCCESS -> {
                progressDialog?.hide()
            }
            is NetworkStatus.ERROR -> {
                Log.d("TAG", "stackcommitfragment: ${it.error}")
                progressDialog?.hide()
            }
        }
    }

    private fun setUpViews() {
        stackTimesAdapter.itemClickListener(this)
        stackDaysAdapter.itemClickListener(this)
        binding.rvStackDate.adapter = stackDaysAdapter
        binding.rvStackTime.adapter = stackTimesAdapter

        binding.btnNavbat.setOnClickListener {
            stackCommit()
            Toast.makeText(requireContext(), "salom", Toast.LENGTH_SHORT).show()}
    }

    private fun setupObserver() {
        viewModel.liveStackDayState.observe(viewLifecycleOwner, stackDaysObserver)
        viewModel.livestackTimeState.observe(viewLifecycleOwner, stackTimesObserver)
        viewModel.liveStackCommit.observe(viewLifecycleOwner, stackCommitObserver)
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
        viewModel.stackCommit(id = doctorId,price = price)
    }

}