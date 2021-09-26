package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tashxis.Application
import com.example.tashxis.R
import com.example.tashxis.business.util.GridSpacingItemDecoration
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.progressDialog
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentShifokorBinding
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewModel.MainViewModel
import com.example.tashxis.framework.viewModel.MainViewModelFactory
import com.example.tashxis.presentation.adapters.SpecialityAdapter
import com.example.tashxis.presentation.adapters.SpecialityClickListener
import com.example.tashxis.presentation.ui.auth.model.main.SpecialityData


class ShifokorFragment : Fragment(R.layout.fragment_shifokor), SpecialityClickListener {
    private var _binding: FragmentShifokorBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val sAdapter: SpecialityAdapter by lazy { SpecialityAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitClient.instance
        viewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(
                Application(),
                MainRepository(api)
            )
        )[MainViewModel::class.java]

        viewModel.getSpeciality()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShifokorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private val specialityObserver = Observer<NetworkStatus<List<SpecialityData>>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                progressDialog?.show()
                Log.i("NetworkStatus", "Loading: ${it}")
            }
            is NetworkStatus.SUCCESS -> {
                progressDialog?.hide()
                Log.i("NetworkStatus", "Succes: ${it.data}")
                sAdapter.submitList(it.data)
            }
            is NetworkStatus.ERROR -> {
                progressDialog?.hide()
                Log.i("NetworkStatus", "Error: ${it.error}")
            }
        }
    }

    private fun setupObserver() {
        viewModel.liveSpecialityState.observe(viewLifecycleOwner, specialityObserver)
    }

    private fun setupView() {

        sAdapter.itemClickListener(this)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSpeciality.layoutManager = layoutManager
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen._8sdp)
        binding.rvSpeciality.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                spacingInPixels,
                true,
                0
            )
        )
        binding.rvSpeciality.adapter = sAdapter

    }

    override fun onClicked(model: SpecialityData) {
        //
    }
}