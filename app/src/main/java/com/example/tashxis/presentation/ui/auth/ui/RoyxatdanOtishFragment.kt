package com.example.tashxis.presentation.ui.auth.ui

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tashxis.R
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.hideKeyboard
import com.example.tashxis.business.util.lazyFast
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.AlertDialogBinding
import com.example.tashxis.databinding.FragmentRoyxatdanOtishBinding
import com.example.tashxis.framework.base.BaseFragment
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.framework.viewModel.AuthViewModel
import com.example.tashxis.framework.viewModel.AuthViewModelFactory
import com.example.tashxis.framework.viewModel.IAuthViewModel
import com.example.tashxis.presentation.ui.activity.MainActivity
import com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse.DistrictData
import com.example.tashxis.presentation.ui.auth.model.auth.ProfileInfoResponse.ProfileEditModel
import com.example.tashxis.presentation.ui.auth.model.auth.RegionData
import com.example.tashxis.presentation.ui.auth.preference.PrefHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*


class RoyxatdanOtishFragment :
    BaseFragment<FragmentRoyxatdanOtishBinding>(FragmentRoyxatdanOtishBinding::inflate),
    AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private val alertDialog by lazy {
        _alertDialogBinding = AlertDialogBinding.inflate(layoutInflater)
        context?.let {
            AlertDialog.Builder(it)
                .setView(alertDialogBinding.root)
                .create()
        }
    }

    private lateinit var authViewModel: IAuthViewModel

    private lateinit var dialog: AlertDialog
    private val editModel by lazyFast { ProfileEditModel() }

    private var _alertDialogBinding: AlertDialogBinding? = null
    private val alertDialogBinding get() = _alertDialogBinding!!

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

        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showDialog()
                }
            })

    }

    private fun showDialog() {
        alertDialog?.show()
        alertDialogBinding.btnOk.setOnClickListener {
            activity?.finish()
        }
        alertDialogBinding.btnCancel.setOnClickListener {
            alertDialog?.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observer()
        setUpListener()
    }

    private fun setUpListener() {
        setFragmentResultListener(CaptureImageFragment.CAMERA_RESULT_KEY) { requestKey, bundle ->
            bundle.getString(CaptureImageFragment.IMAGE_URI_KEY)?.let { authViewModel.setImageUri(it) }
        }
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
                birth_date = editModel.birthDate
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
        binding.ivPhotograph.setOnClickListener {
            showImagePickBottomSheetDialog()
        }
    }

    private fun showImagePickBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_chooser)

        bottomSheetDialog.findViewById<TextView>(R.id.from_gallery)?.setOnClickListener {
            requestReadWritePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.findViewById<TextView>(R.id.from_camera)?.setOnClickListener {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private val requestReadWritePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                requestLauncherGallery.launch(intent)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!requireActivity().shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        dialog = MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Grant Permission")
                            .setMessage("You have to grant permission in the settings")
                            .setPositiveButton("Yes") { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri =
                                    Uri.fromParts("package", requireActivity().packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            }
                            .setNegativeButton("No", null)
                            .create()
                        dialog.show()
                    }
                }
                Toast.makeText(
                    requireContext(),
                    "You have to give permission to take picture",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                findNavController().navigate(R.id.action_royxatdanOtishFragment_to_captureImageFragment)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!requireActivity().shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        dialog = MaterialAlertDialogBuilder(requireContext())
                            .setTitle(getString(R.string.grant_permission))
                            .setMessage(getString(R.string.grant_permission_settings))
                            .setPositiveButton(R.string.positive_button_text) { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri =
                                    Uri.fromParts("package", requireActivity().packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            }
                            .setNegativeButton(R.string.negative_button_text, null)
                            .create()
                        dialog.show()
                    }
                }
                Toast.makeText(
                    requireContext(),
                    "You have to give permission to take picture",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    private val requestLauncherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                authViewModel.setImageUri(imageUri.toString())
                /* val inputStream =  activity?.contentResolver?.openInputStream(imageUri!!)
                 val drawable = Drawable.createFromStream(inputStream, imageUri.toString())*/
            }
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
        authViewModel.imageUri.observe(requireActivity(), { imageUri ->
            context?.let { context ->
                Glide.with(context)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_profile)
                    .centerCrop()
                    .into(binding.ivClient)
            }
            Toast.makeText(requireContext(), "observerda ishlavatti", Toast.LENGTH_SHORT).show()
        })

        authViewModel.liveRegionState.observe(viewLifecycleOwner, { it ->
            when (it) {
                is NetworkStatus.LOADING -> {
                    showProgress()
                }
                is NetworkStatus.SUCCESS -> {
                    hideProgress()
                    val list = mutableListOf<String>()
                    val dataRegion = it.data
                    regions = dataRegion
                    regions?.map { list.add(it.name) }
                    val regionAdapter: ArrayAdapter<*> =
                        ArrayAdapter<Any?>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            list.toList()
                        )
                    binding.spinnerVil.adapter = regionAdapter
                    binding.spinnerVil.onItemSelectedListener = this
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
                            android.R.layout.simple_spinner_item,
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
                    setAppStartStatus(false)
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

    private fun setAppStartStatus(status: Boolean) {
        val pref = requireActivity().getSharedPreferences("SLIDER_APP", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean("APP_START", status)
        editor.apply()
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
        editModel.birthDate = sdf.format(myCalendar.time)
    }


    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
        _alertDialogBinding = null
        super.onDestroy()
    }
}