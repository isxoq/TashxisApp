package com.example.tashxis.ui.auth.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tashxis.Application
import com.example.tashxis.R
import com.example.tashxis.databinding.FragmentLoginBinding
import com.example.tashxis.ui.auth.RegisterActivity
import com.example.tashxis.ui.auth.repo.AuthRepository
import com.example.tashxis.ui.auth.vm.AuthViewModel
import com.example.tashxis.ui.auth.vm.AuthViewModelFactory
import com.example.tashxis.util.Constants
import com.example.tashxis.util.Status
import com.example.tashxis.util.lazyFast
import info.texnoman.texnomart.api.RetrofitClient
import info.texnoman.texnomart.auth.preference.PreferenceManagerImp


class LoginFragment : Fragment() {
    private val TAG = "TAG"
    private var _binding: FragmentLoginBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences by lazyFast {
            PreferenceManagerImp(
                requireContext().getSharedPreferences(
                    Constants.PREF_NAME,
                    Context.MODE_PRIVATE
                )
            )
        }
        val api = RetrofitClient.instance
        authViewModel = ViewModelProvider(
            requireActivity(),
            AuthViewModelFactory(
                Application(),
                AuthRepository(api)
            )
        ).get(AuthViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLoginReg.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnTasdiqlash.setOnClickListener {
            if (binding.telRaqam.text.toString().length == 17) {
                val _phoneNumber ="+998" + binding.telRaqam.rawText.toString()
            authViewModel.login(_phoneNumber)
            } else
                binding.telRaqam.error = "Raqam topilmadi!"
        }

        authViewModel.liveState.observe(viewLifecycleOwner, {
            when (it) {
                Status.LOADING -> {
                    Log.d(TAG, "Login onViewCreated: Loading")
                }
                Status.ERROR -> {
                    Log.d(TAG, "Login onViewCreated: Error")
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "Login onViewCreated: Success")
                    val phoneNumber = binding.telRaqam.text.toString()
                    findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
                    authViewModel.phoneNumber.postValue(phoneNumber)

                }
            }
        })

        authViewModel.toast.observe(viewLifecycleOwner,{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}