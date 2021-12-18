package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber
import uz.tashxis.client.R
import uz.tashxis.client.business.util.NetworkStatus
import uz.tashxis.client.data.RetrofitClient
import uz.tashxis.client.databinding.FragmentAboutDoctorBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.framework.repo.MainRepository
import uz.tashxis.client.framework.viewModel.AboutDoctorViewModel
import uz.tashxis.client.framework.viewModel.AboutDoctorViewModelFactory
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor.AboutDoctorResponseData


class AboutDoctorFragment :
    BaseFragment<FragmentAboutDoctorBinding>(FragmentAboutDoctorBinding::inflate),
    OnMapReadyCallback {
    private val mapFragment by lazy { childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment }
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private lateinit var viewModel: AboutDoctorViewModel
    private var doctorId: Int = 0
    private var price: Int = 0
    private lateinit var map: GoogleMap
    private lateinit var location: LatLng
    private lateinit var hosPitalName: String

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
        setUpViews()
    }

    private fun setUpViews() {
        handler.postDelayed({
            if (view != null) {
                mapFragment.getMapAsync(this)
            }
        }, 1500)

    }

    private val aboutDoctorsObserver = Observer<NetworkStatus<AboutDoctorResponseData>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                showProgress()
                Timber.d("Loading: $it")
            }
            is NetworkStatus.SUCCESS -> {
                hideProgress()
                Timber.d("Success: " + it.data)
                setUpViewSuccess(it)
                doctorId = it.data.id

            }
            is NetworkStatus.ERROR -> {
                hideProgress()
                Timber.d("Error: " + it.error)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViewSuccess(it: NetworkStatus.SUCCESS<AboutDoctorResponseData>) {
        binding.tvFio.text = getString(
            R.string.doctor_fio,
            it.data.firstName, it.data.fatherName, it.data.lastName
        )
        Glide
            .with(uz.tashxis.client.App.context!!)
            .load(uz.tashxis.client.business.util.Constants.BASE_URL + it.data.imageUrl)
            .placeholder(R.drawable.ic_doctor)
            .centerCrop()
            .into(binding.ivDoctor)
        Glide
            .with(uz.tashxis.client.App.context!!)
            .load(uz.tashxis.client.business.util.Constants.BASE_URL + it.data.hospital.imageUrl)
            .placeholder(R.drawable.ic_doctor)
            .centerCrop()
            .into(binding.ivHospital)
        val model = it.data
        location = LatLng(model.hospital.lat.toDouble(), model.hospital.long.toDouble())
        hosPitalName = model.hospital.name
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
        binding.tvYutuqlari.text = getString(
            R.string.yutuqlari,
            model.achievements.let {
                if (it == "") return@let "Mavjud emas" else {
                }
            })
        binding.tvHospital.text = model.hospital.name
        binding.tvManzilText.text = model.hospital.address
        // binding.tvWorkHour.text = model.hospital.


    }

    private fun setupObserver() {
        viewModel.liveAboutDoctorsState.observe(viewLifecycleOwner, aboutDoctorsObserver)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

        map.addMarker(
            MarkerOptions()
                .position(location)
                .title(hosPitalName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
    }




}


