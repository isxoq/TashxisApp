package uz.tashxis.client.presentation.ui.nav_drawer.profile.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.tashxis.client.databinding.FragmentProfilBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.presentation.ui.auth.preference.PrefHelper

class ProfileFragment : BaseFragment<FragmentProfilBinding>(FragmentProfilBinding::inflate) {

    private val pref by lazy { PrefHelper.getPref(uz.tashxis.client.App.context!!) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        Toast.makeText(requireContext(), """${pref.name} ${pref.fathername}""", Toast.LENGTH_SHORT)
            .show()
        binding.etName.setText(pref.name.toString())
        binding.etSureName.setText(pref.surename.toString())
        binding.etThirdName.setText(pref.fathername.toString())
        binding.etBirthDate.setText(pref.birthDate.toString())
        binding.etPhone.setText(pref.phone)

        binding.ivBack.setOnClickListener { findNavController().popBackStack() }
    }

}