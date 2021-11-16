package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tashxis.App
import com.example.tashxis.R
import com.example.tashxis.business.util.Constants
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentAboutDoctorBinding
import com.example.tashxis.framework.base.BaseFragment
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewModel.AboutDoctorViewModel
import com.example.tashxis.framework.viewModel.AboutDoctorViewModelFactory
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor.AboutDoctorResponseData


class AboutDoctorFragment :
    BaseFragment<FragmentAboutDoctorBinding>(FragmentAboutDoctorBinding::inflate) {
    private lateinit var viewModel: AboutDoctorViewModel
    private var doctorId: Int = 0
    private var price: Int = 0

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        binding.btnSetMeeting.setOnClickListener {
            val bundle = bundleOf("id" to doctorId, "price" to price)
            findNavController().navigate(R.id.action_aboutDoctorFragment_to_stackFragment, bundle)
        }
    }

    private val aboutDoctorsObserver = Observer<NetworkStatus<AboutDoctorResponseData>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                showProgress()
                Log.d("NetworkStatus", "Loading: ${it}")

            }
            is NetworkStatus.SUCCESS -> {
                hideProgress()
                Log.d("NetworkStatus", "Succes: ${it.data}")
                setUpViewSuccess(it)
                doctorId = it.data.id

            }
            is NetworkStatus.ERROR -> {
                hideProgress()
                Log.d("NetworkStatus", "Error: ${it.error}")
            }
        }
    }

    private fun setUpViewSuccess(it: NetworkStatus.SUCCESS<AboutDoctorResponseData>) {
        binding.tvFio.text = getString(
            R.string.doctor_fio,
            it.data.firstName, it.data.fatherName, it.data.lastName
        )
        Glide
            .with(App.context!!)
            .load(Constants.BASE_URL + it.data.imageUrl)
            .placeholder(R.drawable.ic_doctor)
            .centerCrop()
            .into(binding.ivDoctor)
        Glide
            .with(App.context!!)
            .load(Constants.BASE_URL + it.data.hospital.imageUrl)
            .placeholder(R.drawable.ic_doctor)
            .centerCrop()
            .into(binding.ivHospital)
        val model = it.data
        price = it.data.acceptanceAmount
        binding.tvDoctorName.text = "DR. ${model.firstName}"
        binding.tvDoctorSpeciality.text = model.speciality.name
        binding.tvQabulPrice.text = getString(R.string.price, model.acceptanceAmount.toString())
        binding.tvDistanceDoctor.text = getString(R.string.distance, model.distance.toString())
        binding.tvStarCount.text = model.rate.toString()
        binding.tvCommentDoctor.text = getString(R.string.string_comment, model.id.toString())
        binding.tvStarCount.text = model.rate.toString()
        binding.tvUniversity.text = getString(R.string.univetsity, model.study)
        binding.tvQualification.text = getString(R.string.qualification, model.workYear.toString())
        binding.tvYutuqlari.text = getString(R.string.yutuqlari, model.achievements)
        binding.tvHospital.text = model.hospital.name


//        binding.tvAge.text = model.age
//        binding.tvLanguage.text = model.
        binding.tvManzilText.text = model.hospital.address
        // binding.tvWorkHour.text = model.hospital.


    }

    private fun setupObserver() {
        viewModel.liveAboutDoctorsState.observe(viewLifecycleOwner, aboutDoctorsObserver)
    }


}