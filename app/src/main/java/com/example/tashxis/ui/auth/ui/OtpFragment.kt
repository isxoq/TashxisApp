package com.example.tashxis.ui.auth.ui

import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import com.example.tashxis.Application
import com.example.tashxis.R
import com.example.tashxis.databinding.FragmentOtpBinding
import com.example.tashxis.ui.auth.repo.AuthRepository
import com.example.tashxis.ui.auth.vm.AuthViewModel
import com.example.tashxis.ui.auth.vm.AuthViewModelFactory
import com.example.tashxis.util.Constants
import com.example.tashxis.util.Status
import info.texnoman.texnomart.api.RetrofitClient

class OtpFragment : Fragment() {
    var logReg = 0
    lateinit var phoneNumber: String
    private val TAG = "TAG"
    private var _binding: FragmentOtpBinding? = null
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
        ).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.logReg.observe(viewLifecycleOwner, {
            logReg = it
        })
        authViewModel.phoneNumber.observe(viewLifecycleOwner, {
            phoneNumber = it
        })
        //test
        //binding/
        binding.smsKod.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.smsKod.rawText =="12345")
                {
                    if (logReg==Constants.REG)
                    {
                        authViewModel.verify_code(phoneNumber,"12345")
                    }
                    if (logReg==Constants.LOG){
                        authViewModel.login_verify(phoneNumber,"12345")
                    }
                }
            }
        })

        authViewModel.liveState.observe(viewLifecycleOwner,{
            when (it) {
                Status.LOADING -> {
                    Log.d(TAG, "OTP LOG-REG onViewCreated:LOADING ")
                }
                Status.ERROR -> {
                    Log.d(TAG, "Login onViewCreated: Error")
                }
                Status.SUCCESS -> {

                    if (logReg==Constants.REG) {

                        Log.d(TAG, "OTP REG onViewCreated:Success ")
                        //TODO
                        findNavController().navigate(R.id.action_otpFragment_to_royxatdanOtishFragment)
                    }
                    if (logReg==Constants.LOG)
                    {

                        Log.d(TAG, "OTP LOG onViewCreated: Success")
                        findNavController().navigate(R.id.action_otpFragment_to_nav_graph2)
                    }

                }
            }
        })
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

