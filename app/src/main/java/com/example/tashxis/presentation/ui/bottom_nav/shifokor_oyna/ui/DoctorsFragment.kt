package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tashxis.R
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.progressDialog
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentDoctorsBinding
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewModel.DoctorViewModel
import com.example.tashxis.framework.viewModel.DoctorViewModelFactory
import com.example.tashxis.presentation.adapters.DoctorsAdapter
import com.example.tashxis.presentation.adapters.DoctorsClickListener
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response.DoctorResponseData

class DoctorsFragment : Fragment(R.layout.fragment_doctors), DoctorsClickListener {
    private var _binding: FragmentDoctorsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DoctorViewModel
    private val doctorsAdapter: DoctorsAdapter by lazy { DoctorsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = RetrofitClient.instance
        viewModel = ViewModelProvider(
            requireActivity(),
            DoctorViewModelFactory(
                requireActivity().application,
                MainRepository(api)
            )
        )[DoctorViewModel::class.java]
        val id = arguments?.getInt("id")
        viewModel.getDoctors(id!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDoctorsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private val doctorsObserver = Observer<NetworkStatus<List<DoctorResponseData>>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                progressDialog?.show()
                Log.d("NetworkStatus", "Loading: ${it}")
            }
            is NetworkStatus.SUCCESS -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Succes: ${it.data}")
                doctorsAdapter.submitList(it.data)
                binding.toolbarDoctors.title = it.data[0].speciality.name.toString()

            }
            is NetworkStatus.ERROR -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Error: ${it.error}")
            }
        }
    }

    private fun setupObserver() {
        viewModel.liveDoctorsState.observe(viewLifecycleOwner, doctorsObserver)
    }

    private fun setupView() {
        doctorsAdapter.itemClickListener(this)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen._8sdp)
        binding.rvDoctors.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvDoctors.adapter = doctorsAdapter
    }

    override fun onClicked(model: DoctorResponseData) {

    }
}



