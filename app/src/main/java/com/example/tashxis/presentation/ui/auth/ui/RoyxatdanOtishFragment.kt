package com.example.tashxis.presentation.ui.auth.ui

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.App
import com.example.tashxis.business.util.Constants
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.hideKeyboard
import com.example.tashxis.business.util.lazyFast
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentRoyxatdanOtishBinding
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.framework.viewModel.AuthViewModel
import com.example.tashxis.framework.viewModel.AuthViewModelFactory
import com.example.tashxis.presentation.ui.activity.MainActivity
import com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse.DistrictData
import com.example.tashxis.presentation.ui.auth.model.auth.ProfileInfoResponse.ProfileEditModel
import com.example.tashxis.presentation.ui.auth.model.auth.RegionData
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefsImpl
import java.text.SimpleDateFormat
import java.util.*


class RoyxatdanOtishFragment : Fragment(),
    AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentRoyxatdanOtishBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private val binding get() = _binding!!
    private val editModel by lazyFast { ProfileEditModel() }
    private val TAG = "TAG"
    private var preferences: TashxisPrefs? = null

    init {
        if (App.context != null) {
            val prefs = App.context!!.getSharedPreferences(
                Constants.PREF_NAME,
                Context.MODE_PRIVATE
            )
            preferences = TashxisPrefsImpl(prefs)
        }
    }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoyxatdanOtishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observer()
        setUpListener()
    }

    private fun collectModel() {
        editModel.auth_key = preferences?.token ?: ""
        editModel.birth_date = binding.etBirthDate.text.toString()
        editModel.father_name = binding.etThirdName.text.toString()
        editModel.last_name = binding.etSureName.text.toString()
        if (binding.rbMale.isChecked) {
            editModel.gender = 1
        } else {
            editModel.gender = 0
        }
        editModel.father_name = binding.etThirdName.text.toString()

    }


    private fun setUpListener() {
        binding.btnRegisterCommit.setOnClickListener {
            //collectModel()
            authViewModel.add_profile_info(
                "e_Zb1oAe_i6BiTu7gO4T_2LfMeQTUig0",
                "sffsdffsd",
                "editModel.last_name",
                "editModel.father_name",
                1,
                1,
                2,
                "editModel.birth_date"
            )
/*
            authViewModel.add_profile_info(
                editModel.auth_key,
                editModel.first_name,
                editModel.last_name,
                editModel.father_name,
                editModel.gender,
                editModel.province_id,
                editModel.region_id,
                editModel.birth_date
            )
*/

        }
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
        authViewModel.liveRegionState.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkStatus.LOADING -> {
                }
                is NetworkStatus.SUCCESS -> {
                    Log.i(TAG, "observer: ${it.data}")
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
                    binding.spinnerRegion.adapter = regionAdapter
                    binding.spinnerRegion.onItemSelectedListener = this
                }
                is NetworkStatus.ERROR -> {
                }
            }
        })
        authViewModel.liveDistrictState.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkStatus.LOADING -> {
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
                    binding.spinnerDistrict.adapter = regionAdapter
                    binding.spinnerDistrict.onItemSelectedListener = this
                }
                is NetworkStatus.ERROR -> {
                }
            }
        })
        authViewModel.liveProfileInfoState.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkStatus.SUCCESS -> {
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity?.startActivity(intent)
                }
                is NetworkStatus.LOADING -> {

                }
                is NetworkStatus.ERROR -> {

                }
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            binding.spinnerRegion.id -> {
                if (regions != null)
                    authViewModel.getDistrict(regions!![position].id)
                editModel.region_id = regions!![position].id
            }
            binding.spinnerDistrict.id -> {
                if (districts != null) {
                    editModel.province_id = districts!![position].id
                }
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


}