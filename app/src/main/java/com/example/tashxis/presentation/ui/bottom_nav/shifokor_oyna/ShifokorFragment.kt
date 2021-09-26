package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tashxis.R
import com.example.tashxis.databinding.FragmentShifokorBinding

class ShifokorFragment : Fragment(R.layout.fragment_shifokor) {
    private var _binding: FragmentShifokorBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shifokor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}