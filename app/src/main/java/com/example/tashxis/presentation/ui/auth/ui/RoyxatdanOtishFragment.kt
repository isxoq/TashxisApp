package com.example.tashxis.presentation.ui.auth.ui

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.hideKeyboard
import com.example.tashxis.business.util.lazyFast
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentRoyxatdanOtishBinding
import com.example.tashxis.framework.base.BaseFragment
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.framework.viewModel.AuthViewModel
import com.example.tashxis.framework.viewModel.AuthViewModelFactory
import com.example.tashxis.presentation.ui.activity.MainActivity
import com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse.DistrictData
import com.example.tashxis.presentation.ui.auth.model.auth.ProfileInfoResponse.ProfileEditModel
import com.example.tashxis.presentation.ui.auth.model.auth.RegionData
import com.example.tashxis.presentation.ui.auth.preference.PrefHelper
import java.text.SimpleDateFormat
import java.util.*


class RoyxatdanOtishFragment :
    BaseFragment<FragmentRoyxatdanOtishBinding>(FragmentRoyxatdanOtishBinding::inflate),
    AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private lateinit var authViewModel: AuthViewModel
    private val editModel by lazyFast { ProfileEditModel() }
    private val TAG = "TAG"
    private val pref by lazyFast { PrefHelper.getPref(requireContext()) }
    private var regions: List<RegionData>? = null
    private var districts: List<DistrictData>? = null
    private var myCalendar: Calendar = Calendar.getInstance()
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = RetrofitClient.instance
        authViewModel = ViewModelProvider(
            requireActivity(),
            AuthViewModelFactory(
                requireActivity().application,
                AuthRepository(api)
            )
        )[AuthViewModel::class.java]
        authViewModel.getRegion()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observer()
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnTasdiqlash.setOnClickListener {
            sobratKartoshku()
            authViewModel.addProfileInfo(
                pref.token.toString(),
                first_name = editModel.first_name,
                last_name = editModel.last_name,
                father_name = editModel.father_name,
                gender = editModel.gender,
                province_id = editModel.province_id,
                region_id = editModel.region_id,
                birth_date = editModel.birth_date
            )
        }
    }

    private fun sobratKartoshku() {
        editModel.first_name = binding.etName.text.toString()
        editModel.father_name = binding.etThirdName.text.toString()
        editModel.last_name = binding.etSureName.text.toString()
        editModel.auth_key = pref.token.toString()
    }

    private fun setupView() {
        binding.etBirthDate.setOnClickListener { setDataListener() }
        binding.ivBirthDate.setOnClickListener { setDataListener() }
    }

    private fun setDataListener() {
        hideKeyboard()
        val datePickerDialog = DatePickerDialog(
            requireContext(), this, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        val calendar = Calendar.getInstance()
        calendar.set(1920, 1, 1)
        datePickerDialog.datePicker.minDate = calendar.time.time
        datePickerDialog.datePicker.maxDate = Date().time
        datePickerDialog.show()
    }


    private fun observer() {
        authViewModel.liveRegionState.observe(viewLifecycleOwner, { it ->
            when (it) {
                is NetworkStatus.LOADING -> {
                    showProgress()
                }
                is NetworkStatus.SUCCESS -> {
                    val list = mutableListOf<String>()
                    val dataRegion = it.data
                    regions = dataRegion
                    regions?.map { list.add(it.name) }
                    val regionAdapter: ArrayAdapter<*> =
                        ArrayAdapter<Any?>(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            list.toList()
                        )
                    binding.spinnerVil.adapter = regionAdapter
                    binding.spinnerVil.onItemSelectedListener = this
                    hideProgress()
                }
                is NetworkStatus.ERROR -> {
                    hideProgress()
                    Toast.makeText(requireContext(), "Xatolik yuz berdi", Toast.LENGTH_SHORT).show()
                }
            }
        })
        authViewModel.liveDistrictState.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkStatus.LOADING -> {
                    showProgress()
                }
                is NetworkStatus.SUCCESS -> {
                    val list = mutableListOf<String>()
                    val dataDistrict = it.data
                    districts = dataDistrict
                    districts?.map { list.add(it.name) }
                    val regionAdapter: ArrayAdapter<*> =
                        ArrayAdapter<Any?>(
                            requireContext(),
                            R.layout.simple_spinner_item,
                            list.toList()
                        )
                    binding.spinnerTum.adapter = regionAdapter
                    binding.spinnerTum.onItemSelectedListener = this
                    hideProgress()
                }
                is NetworkStatus.ERROR -> {
                    hideProgress()
                    //TODO
                }
            }
        })
        authViewModel.liveProfileInfoState.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkStatus.SUCCESS -> {
                    hideProgress()
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity?.startActivity(intent)
                }
                is NetworkStatus.LOADING -> {
                    showProgress()

                }
                is NetworkStatus.ERROR -> {
                    hideProgress()
                    Toast.makeText(requireContext(), "Xatolik yuz berdi", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            binding.spinnerVil.id -> {
                Log.d(TAG, "onItemSelected: ${regions!![position].name}")
                authViewModel.getDistrict(regions!![position].id)
                editModel.region_id = regions!![position].id
            }
            binding.spinnerTum.id -> {
                if (districts != null) {
                    editModel.province_id = districts!![position].id
                } else {
                    Log.d(TAG, "onItemSelected: Ko'cha topilmadi")
                }
            }
            binding.rbMale.id -> {
                editModel.gender = 1
            }
            binding.rbFemale.id -> {
                editModel.gender = 2
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        binding.etBirthDate.setText(sdf.format(myCalendar.time))
        editModel.birth_date = sdf.format(myCalendar.time)
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }
}