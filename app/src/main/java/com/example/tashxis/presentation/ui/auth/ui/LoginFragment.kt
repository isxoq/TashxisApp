package com.example.tashxis.presentation.ui.auth.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tashxis.App
import com.example.tashxis.R
import com.example.tashxis.business.util.Status
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentLoginBinding
import com.example.tashxis.framework.base.BaseFragment
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.framework.viewModel.AuthViewModel
import com.example.tashxis.framework.viewModel.AuthViewModelFactory
import com.example.tashxis.presentation.ui.auth.preference.PrefHelper


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val TAG = "TAG"
    private lateinit var authViewModel: AuthViewModel
    private val pref by lazy { PrefHelper.getPref(App.context!!) }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(pref.phone!=""&&pref.name==null&&pref.fathername==null){
            findNavController().navigate(R.id.action_loginFragment_to_royxatdanOtishFragment)
        }
        binding.tvLoginReg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnTasdiqlash.setOnClickListener {
            if (binding.telRaqam.text.toString().length == 17) {
                val phoneNumber = "+998" + binding.telRaqam.rawText.toString()
                authViewModel.login(phoneNumber)
            } else
                binding.telRaqam.error = "Raqam topilmadi!"
        }

        authViewModel.liveLoginState.observe(viewLifecycleOwner, {
            when (it) {
                Status.LOADING -> {
                    Log.d(TAG, "Login onViewCreated: Loading")
                    showProgress()
                }
                Status.ERROR -> {
                    hideProgress()
                    Log.d(TAG, "Login onViewCreated: Error")
                }
                Status.SUCCESS -> {
                    hideProgress()
                    Log.d(TAG, "Login onViewCreated: Success")
                    val phoneNumber = binding.telRaqam.text.toString()
                    authViewModel.phoneNumber.postValue(phoneNumber)
                    findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
                }
            }
        })

        authViewModel.toast.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

}