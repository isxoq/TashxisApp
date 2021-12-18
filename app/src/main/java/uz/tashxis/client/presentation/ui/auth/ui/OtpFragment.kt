package uz.tashxis.client.presentation.ui.auth.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.tashxis.client.R
import uz.tashxis.client.business.util.Status
import uz.tashxis.client.business.util.hideKeyboard
import uz.tashxis.client.business.util.lazyFast
import uz.tashxis.client.business.util.showKeyboard
import uz.tashxis.client.data.RetrofitClient
import uz.tashxis.client.databinding.FragmentOtpBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.framework.repo.AuthRepository
import uz.tashxis.client.framework.viewModel.AuthViewModel
import uz.tashxis.client.framework.viewModel.AuthViewModelFactory
import uz.tashxis.client.presentation.ui.activity.MainActivity
import uz.tashxis.client.presentation.ui.auth.preference.PrefHelper

class OtpFragment : BaseFragment<FragmentOtpBinding>(FragmentOtpBinding::inflate) {
    var logReg = 0
    lateinit var phoneNumber: String
    private val TAG = "TAG"
    private lateinit var authViewModel: AuthViewModel
    private val preference by lazyFast { PrefHelper.getPref(uz.tashxis.client.App.context!!) }


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
        setUpVIews()
        setListeners()
        setTimer()

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

    private fun setUpVIews() {
        binding.smsKod.showKeyboard()
    }

    private var countDownTimer: CountDownTimer? = null
    private fun setTimer() {
        if (countDownTimer == null) {
            countDownTimer = object : CountDownTimer(12000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    try {
                        binding.resendSms.isVisible = true
                    } catch (e: Exception) {
                        Log.d(TAG, "onTick: ${e.message}")
                    }
                    //
                    val sec = (millisUntilFinished / 1000) % 60
                    val min = (millisUntilFinished / (1000 * 60)) % 60
                    val formattedTimeStr = if (sec <= 9) {
                        "0$min : 0$sec"
                    } else {
                        "0$min : $sec"
                    }
                    binding.tvTimer.text = formattedTimeStr
                }

                override fun onFinish() {
                    try {
                        binding.resendSms.isVisible = true
                    } catch (e: Exception) {
                        context?.let {
                            Toast.makeText(it, "${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }
        countDownTimer?.start()

    }

    private fun setListeners() {
        binding.resendSms.setOnClickListener {
            if (logReg == uz.tashxis.client.business.util.Constants.LOG) {
                authViewModel.login(phoneNumber)
            } else {
                authViewModel.register(phoneNumber)
            }
            setTimer()
        }
        binding.smsKod.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p2 == 9) {
                    if (logReg == uz.tashxis.client.business.util.Constants.LOG) {
                        Log.d("TAG", "onTextChanged: Working")
                        authViewModel.loginVerify(phoneNumber, "12345")
                    } else {
                        authViewModel.verifyCode(phoneNumber, "12345")
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        authViewModel.liveLoginVerifyState.observe(viewLifecycleOwner, {
            when (it) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.ERROR -> {
                    hideProgress()
                }
                Status.SUCCESS -> {
                    hideProgress()
                    countDownTimer?.cancel()
                    if (logReg == uz.tashxis.client.business.util.Constants.REG) {
                        findNavController().navigate(R.id.action_otpFragment_to_royxatdanOtishFragment)
                    }
                    if (logReg == uz.tashxis.client.business.util.Constants.LOG) {
                        if (preference.name == null) {
                            navigation()
                        } else {
                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            activity?.startActivity(intent)
                        }
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

    fun navigation() {
        findNavController().navigate(R.id.action_otpFragment_to_royxatdanOtishFragment)
    }

    override fun onDestroy() {
        hideKeyboard()
        countDownTimer?.cancel()
        super.onDestroy()
    }
}
