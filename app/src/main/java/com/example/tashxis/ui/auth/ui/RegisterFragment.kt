package com.example.tashxis.ui.auth.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.tashxis.databinding.FragmentRegisterBinding
import com.example.tashxis.ui.auth.repo.AuthRepository
import com.example.tashxis.ui.auth.vm.AuthViewModel
import com.example.tashxis.ui.auth.vm.AuthViewModelFactory
import com.example.tashxis.util.Status
import info.texnoman.texnomart.api.RetrofitClient

class RegisterFragment : Fragment() {
    private val TAG = "TAG"
    private var _binding: FragmentRegisterBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = RetrofitClient.instance
        authViewModel = ViewModelProvider(
            requireActivity(),
            AuthViewModelFactory(
                Application(),
                AuthRepository(api)
            )
        )[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.telRaqam.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.telRaqam.text.toString().length == 17){
                    val _phoneNumber = "+998" + binding.telRaqam.rawText.toString()
                    authViewModel.register(_phoneNumber)
                    binding.btnTasdiqlash.isEnabled = true
                }
            }

        })
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
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}