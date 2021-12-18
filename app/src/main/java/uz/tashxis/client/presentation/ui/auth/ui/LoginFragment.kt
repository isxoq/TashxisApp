package uz.tashxis.client.presentation.ui.auth.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import uz.tashxis.client.R
import uz.tashxis.client.business.util.Status
import uz.tashxis.client.business.util.showKeyboard
import uz.tashxis.client.data.RetrofitClient
import uz.tashxis.client.databinding.FragmentLoginBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.framework.repo.AuthRepository
import uz.tashxis.client.framework.viewModel.AuthViewModel
import uz.tashxis.client.framework.viewModel.AuthViewModelFactory
import uz.tashxis.client.presentation.ui.auth.preference.PrefHelper


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val TAG = "TAG"
    private lateinit var authViewModel: AuthViewModel
    private val pref by lazy { PrefHelper.getPref(uz.tashxis.client.App.context!!) }
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
        binding.telRaqam.showKeyboard()

        if (pref.phone != "" && pref.name == null && pref.fathername == null) {
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
                    Timber.d("onDataChange: $it")
                    showProgress()
                }
                Status.ERROR -> {
                    hideProgress()
                    Timber.d("Login onViewCreated: Error")
                }
                Status.SUCCESS -> {
                    hideProgress()
                    Timber.d("Login onViewCreated: Success")
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