package com.example.tashxis.presentation.ui.auth.ui

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import com.example.tashxis.presentation.ui.auth.preference.PrefHelper
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*


class RoyxatdanOtishFragment : Fragment(),
    AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentRoyxatdanOtishBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private val binding get() = _binding!!
    private val editModel by lazyFast { ProfileEditModel() }
    private val TAG = "TAG"
    private val cd = CompositeDisposable()
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
        setUpValidate()
        setUpListener()
    }

    private fun isValidate(
        birthDate: CharSequence,
        etName: CharSequence,
        etSureName: CharSequence,
        etThirdName: CharSequence
    ): Boolean {
        if (birthDate.isEmpty()) {
            binding.etBirthDate.error = "Ushbu maydon to'ldirilishi shart"
        } else {
            binding.etBirthDate.error = null
        }
        if (etName.isEmpty()) {
            binding.etName.error = "Ushbu maydon to'ldirilishi shart"
        } else {
            binding.etName.error = null
        }
        if (etSureName.isEmpty()) {
            binding.etSureName.error = "Ushbu maydon to'ldirilishi shart"
        } else {
            binding.etSureName.error = null
        }
        if (etThirdName.isEmpty()) {
            binding.etThirdName.error = "Ushbu maydon to'ldirilishi shart"
        } else {
            binding.etThirdName.error = null
        }
        return etName.isNotEmpty() &&
                etSureName.isNotEmpty() &&
                etThirdName.isNotEmpty() &&
                birthDate.isNotEmpty()

    }

    private fun setUpValidate() {
        val d = Observable.combineLatest(
            mutableListOf(
                binding.etBirthDate.textChanges().skipInitialValue(),
                binding.etName.textChanges().skipInitialValue(),
                binding.etSureName.textChanges().skipInitialValue(),
                binding.etThirdName.textChanges().skipInitialValue()
            )
        ) {
            isValidate(
                it[0] as CharSequence,
                it[1] as CharSequence,
                it[2] as CharSequence,
                it[3] as CharSequence
            )
        }.doOnNext {
            binding.btnTasdiqlash.isEnabled = it
        }
            .subscribe()
        cd.add(d)
    }


    private fun setUpListener() {
        binding.btnTasdiqlash.setOnClickListener {
            if (binding.etName.text == null) {
                binding.etName.error = "Bu maydon to'ldirilishi shart"
            }
            if (binding.etSureName.text == null) {
                binding.etSureName.error = "Bu maydon to'ldirilishi shart"
            }
            if (binding.etThirdName.text == null) {
                binding.etThirdName.error = "Bu maydon to'ldirilishi shart"
            }
            if (binding.etBirthDate.text == null) {
                binding.etBirthDate.error = "Bu maydon to'ldirilishi shart"
            }


            Toast.makeText(requireContext(), "${pref.token}", Toast.LENGTH_SHORT).show()
            authViewModel.add_profile_info(
                pref.token.toString(),
                "sffsdffsd",
                "editModel.last_name",
                "editModel.father_name",
                1,
                1,
                2,
                "editModel.birth_date"
            )
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
        authViewModel.liveRegionState.observe(viewLifecycleOwner, { it ->
            when (it) {
                is NetworkStatus.LOADING -> {
                    //TODO()
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
                    binding.spinnerVil.adapter = regionAdapter
                    binding.spinnerTum.onItemSelectedListener = this
                }
                is NetworkStatus.ERROR -> {
                    //TODO()
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
                    binding.spinnerTum.adapter = regionAdapter
                    binding.spinnerTum.onItemSelectedListener = this
                }
                is NetworkStatus.ERROR -> {
                    //TODO
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
                    //TODO

                }
                is NetworkStatus.ERROR -> {
                    //TODO

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
            binding.spinnerVil.id -> {
                if (regions != null)
                    authViewModel.getDistrict(regions!![position].id)
                editModel.region_id = regions!![position].id
            }
            binding.spinnerTum.id -> {
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

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun onDestroyView() {
        cd.clear()
        super.onDestroyView()
    }
}