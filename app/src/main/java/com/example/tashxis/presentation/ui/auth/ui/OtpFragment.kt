package com.example.tashxis.presentation.ui.auth.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.tashxis.R
import com.example.tashxis.business.util.Constants
import com.example.tashxis.business.util.Status
import com.example.tashxis.business.util.hideKeyboard
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentOtpBinding
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.framework.viewModel.AuthViewModel
import com.example.tashxis.framework.viewModel.AuthViewModelFactory
import com.example.tashxis.presentation.ui.activity.MainActivity
import com.google.android.material.textview.MaterialTextView

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
                requireActivity().application,
                AuthRepository(api)
            )
        )[AuthViewModel::class.java]
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
        setListeners()
        setTimer(binding.tvTimer)


        authViewModel.logReg.observe(viewLifecycleOwner, {
            logReg = it
        })
        authViewModel.phoneNumber.observe(viewLifecycleOwner, {
            binding.tvSmsKod.text = getString(R.string.sms_string, it)
            phoneNumber = it

        })
        //test
        //binding/
    }

    private fun setTimer(tvTimer: MaterialTextView) {
        object : CountDownTimer(12000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.resendSms.visibility = View.INVISIBLE
                val sec = (millisUntilFinished / 1000) % 60
                val min = (millisUntilFinished / (1000 * 60)) % 60
                val formattedTimeStr = if (sec <= 9) {
                    "0$min : 0$sec"
                } else {
                    "0$min : $sec"
                }
                tvTimer.text = formattedTimeStr
            }

            override fun onFinish() {
                try {
                    binding.resendSms.visibility = View.VISIBLE
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), "${e.message}", Toast.LENGTH_SHORT).show()
                }

            }
        }.start()

    }

    private fun setListeners() {
        binding.resendSms.setOnClickListener {
            if (logReg == Constants.LOG) {
                authViewModel.login(phoneNumber)
            } else {
                authViewModel.register(phoneNumber)
            }
            setTimer(binding.tvTimer)
        }
        binding.smsKod.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /*  if (p0 != null) {
                      Log.d(TAG, "onTextChanged:p0 ${p0.length}")
                  }
                  if (p1 != null) {
                      Log.d(TAG, "onTextChanged:p1 ${p1}")
                  }
                  if (p2 != null) {
                      Log.d(TAG, "onTextChanged:p2 ${p2}")
                  }*/
                if (p2 == 9) {
                    Log.d(TAG, "onTextChanged: p3")
                    if (logReg == Constants.LOG) {
                        authViewModel.login_verify(phoneNumber, "12345")
                    } else {
                        authViewModel.verify_code(phoneNumber, "12345")
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        authViewModel.liveState.observe(viewLifecycleOwner, {
            when (it) {
                Status.LOADING -> {
                    Log.d(TAG, "OTP LOG-REG onViewCreated:LOADING ")
                    //TODO
                }
                Status.ERROR -> {
                    //TODO
                    Log.d(TAG, "OTP Log onViewCreated: Error")
                }
                Status.SUCCESS -> {
                    if (logReg == Constants.REG) {
                        findNavController().navigate(R.id.action_otpFragment_to_royxatdanOtishFragment)
//                        val intent  = Intent(requireActivity(), MainActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        activity?.startActivity(intent)
                    }
                    if (logReg == Constants.LOG) {
                        Log.d(TAG, "OTP LOG onViewCreated: Success")
//                        findNavController().navigate(R.id.action_otpFragment_to_nav_graph2)
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        activity?.startActivity(intent)
                    }

                }
            }
        })
        authViewModel.logReg.observe(viewLifecycleOwner, {
            logReg = it
        })
        authViewModel.phoneNumber.observe(viewLifecycleOwner, {
            binding.tvSmsKod.text = getString(R.string.sms_string, it)
            phoneNumber = it
        })

    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }
    override fun onDestroy() {
        hideKeyboard()
        super.onDestroy()
    }
}

