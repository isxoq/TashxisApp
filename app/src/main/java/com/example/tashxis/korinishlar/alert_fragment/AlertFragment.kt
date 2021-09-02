package com.example.tashxis.korinishlar.alert_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tashxis.R
import com.example.tashxis.databinding.FragmentAlertBinding

class AlertFragment : Fragment() {

    lateinit var binding: FragmentAlertBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertBinding.inflate(layoutInflater)
        return binding.root



    }

    companion object {
        @JvmStatic
        fun newInstance() = AlertFragment()
    }
}