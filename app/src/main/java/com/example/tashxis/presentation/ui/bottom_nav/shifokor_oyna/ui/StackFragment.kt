package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.R
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.progressDialog
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentStackBinding
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewModel.StackViewModel
import com.example.tashxis.framework.viewModel.StackViewModelFactory
import com.example.tashxis.presentation.adapters.StackDaysAdapter
import com.example.tashxis.presentation.adapters.StackDaysClickListener
import com.example.tashxis.presentation.adapters.StackTimesAdapter
import com.example.tashxis.presentation.adapters.StackTimesClickListener
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.StackDaysData


class StackFragment : Fragment(R.layout.fragment_stack), StackDaysClickListener,
    StackTimesClickListener {
    private var _binding: FragmentStackBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StackViewModel
    private val stackDaysAdapter: StackDaysAdapter by lazy { StackDaysAdapter() }
    private val stackTimesAdapter: StackTimesAdapter by lazy { StackTimesAdapter() }
    private var param1: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt("id")
            val api = RetrofitClient.instance
            viewModel = ViewModelProvider(
                requireActivity(),
                StackViewModelFactory(requireActivity().application, MainRepository(api))
            )[StackViewModel::class.java]
            viewModel.getStackDays(param1)
            viewModel.getStackTimes(param1)
        }
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

    private fun setUpViews() {
        stackTimesAdapter.itemClickListener(this)
        stackDaysAdapter.itemClickListener(this)
        binding.rvStackDate.adapter = stackDaysAdapter
        binding.rvStackTime.adapter = stackTimesAdapter
    }

    private fun setupObserver() {
        viewModel.liveStackDayState.observe(viewLifecycleOwner, stackDaysObserver)
        viewModel.livestackTimeState.observe(viewLifecycleOwner, stackTimesObserver)
    }

    private var selectedDatePos = -1
    override fun onDateClicked(model: StackDaysData, position: Int) {
        if (selectedDatePos != -1)
            stackDaysAdapter.notifyItemChanged(selectedDatePos, StackDaysAdapter.UNSELECTED_DATE)
        stackDaysAdapter.notifyItemChanged(position, StackDaysAdapter.SELECTED_DATE)
        selectedDatePos = position
        //TODO
    }

    private var selectedTimePos = -1
    override fun onTimeClicked(model: String, position: Int) {
        if (selectedTimePos != -1)
            stackTimesAdapter.notifyItemChanged(selectedTimePos, StackTimesAdapter.UNSELECTED_TIME)
        stackTimesAdapter.notifyItemChanged(position, StackTimesAdapter.SELECTED_TIME)
        selectedTimePos = position
        //TODO
    }

}