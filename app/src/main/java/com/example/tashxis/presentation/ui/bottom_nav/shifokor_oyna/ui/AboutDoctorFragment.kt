package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tashxis.App
import com.example.tashxis.R
import com.example.tashxis.business.util.Constants
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.progressDialog
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentAboutDoctorBinding
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewModel.AboutDoctorViewModel
import com.example.tashxis.framework.viewModel.AboutDoctorViewModelFactory
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor.AboutDoctorResponseData


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
       //  Inflate the layout for this fragment
        _binding = FragmentAboutDoctorBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
    }
    private val aboutDoctorsObserver = Observer<NetworkStatus<AboutDoctorResponseData>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                progressDialog?.show()
                Log.d("NetworkStatus", "Loading: ${it}")

            }
            is NetworkStatus.SUCCESS -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Succes: ${it.data}")
                setUpViewSuccess(it)

            }
            is NetworkStatus.ERROR -> {
                progressDialog?.hide()
                Log.d("NetworkStatus", "Error: ${it.error}")
            }
        }
    }

    private fun setUpViewSuccess(it: NetworkStatus.SUCCESS<AboutDoctorResponseData>) {
        binding.tvFio.text = getString(R.string.doctor_fio,
            it.data.firstName, it.data.fatherName, it.data.lastName
        )
        Glide
            .with(App.context!!)
            .load(Constants.BASE_URL+it.data.imageUrl)
            .placeholder(R.drawable.ic_doctor)
            .centerCrop()
            .into(binding.ivDoctor)
        Glide
            .with(App.context!!)
            .load(Constants.BASE_URL+it.data.hospital.imageUrl)
            .placeholder(R.drawable.ic_doctor)
            .centerCrop()
            .into(binding.ivHospital)
        val model = it.data
        binding.tvDoctorName.text = "DR. ${model.firstName}"
        binding.tvDoctorSpeciality.text = model.speciality.name
        binding.tvQabulPrice.text = getString(R.string.price,model.acceptanceAmount.toString())
        binding.tvDistanceDoctor.text = getString(R.string.distance,model.distance.toString())
        binding.tvStarCount.text = model.rate.toString()
        binding.tvCommentDoctor.text = getString(R.string.string_comment,model.id.toString())
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