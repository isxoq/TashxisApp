package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.R
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.progressDialog
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentAboutDoctorBinding
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewModel.AboutDoctorViewModel
import com.example.tashxis.framework.viewModel.AboutDoctorViewModelFactory
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor.AboutDoctorResponseData
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response.DoctorResponseData
import okhttp3.internal.http.HttpMethod


class AboutDoctorFragment : Fragment(R.layout.fragment_about_doctor) {
    private var _binding: FragmentAboutDoctorBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AboutDoctorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = RetrofitClient.instance
        viewModel = ViewModelProvider(
            requireActivity(),
            AboutDoctorViewModelFactory(
                requireActivity().application,
                MainRepository(api)
            )
        )[AboutDoctorViewModel::class.java]
        val id = arguments?.getInt("id")
        viewModel.getAboutDoctors(id!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutDoctorBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }
    private val doctorsObserver = Observer<NetworkStatus<AboutDoctorResponseData>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                progressDialog?.show()
                Log.d("NetworkStatus", "Loading: ${it}")
            }
            is NetworkStatus.SUCCESS -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Succes: ${it.data}")
                //doctorsAdapter.submitList(it.data)
                //binding.toolbarDoctors.title = it.data[0].speciality.name.toString()
            }
            is NetworkStatus.ERROR -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Error: ${it.error}")
            }
        }
    }

    private fun setupObserver() {
        viewModel.liveAboutDoctorsState
    }

    private fun setupView() {
        TODO("Not yet implemented")
    }

}