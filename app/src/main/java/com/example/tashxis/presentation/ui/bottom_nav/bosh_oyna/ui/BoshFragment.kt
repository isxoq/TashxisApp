package com.example.tashxis.presentation.ui.bottom_nav.bosh_oyna.ui

import android.os.Bundle
import android.view.View
import com.example.tashxis.business.util.lazyFast
import com.example.tashxis.databinding.FragmentBoshBinding
import com.example.tashxis.framework.base.BaseFragment
import com.example.tashxis.presentation.ui.auth.preference.PrefHelper

class BoshFragment : BaseFragment<FragmentBoshBinding>(FragmentBoshBinding::inflate) {
    private val preferences by lazyFast { PrefHelper.getPref(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvIsm.text = preferences.name
    }
}