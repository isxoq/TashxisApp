package com.example.tashxis.presentation.ui.auth.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tashxis.R
import com.example.tashxis.business.util.Status
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentRegisterBinding
import com.example.tashxis.framework.base.BaseFragment
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.framework.viewModel.AuthViewModel
import com.example.tashxis.framework.viewModel.AuthViewModelFactory

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val TAG = "TAG"
    private lateinit var authViewModel: AuthViewModel

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
    }

    override fun viewCreated() {
        super.viewCreated()
        binding.telRaqam.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.telRaqam.rawText.length == 9) {
                    val _phoneNumber = "+998" + binding.telRaqam.rawText.trim()
                    binding.btnTasdiqlash.isEnabled = true
                }
            }

        })
        binding.btnTasdiqlash.setOnClickListener {
            authViewModel.register("+998" + binding.telRaqam.rawText.trim())

        }
        authViewModel.liveState.observe(viewLifecycleOwner, {
            when (it) {
                Status.LOADING -> {
                    Log.d(TAG, "Register onViewCreated: Loading")
                }
                Status.ERROR -> {
                    Log.d(TAG, "Register onViewCreated: Error")
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "Register onViewCreated: Success")
                    authViewModel.phoneNumber.postValue("+998" + binding.telRaqam.rawText.toString())
                    findNavController().navigate(R.id.action_registerFragment_to_otpFragment)
                }
            }
        })
        authViewModel.toast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

}