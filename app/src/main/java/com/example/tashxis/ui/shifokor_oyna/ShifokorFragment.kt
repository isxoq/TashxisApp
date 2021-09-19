package com.example.tashxis.ui.shifokor_oyna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tashxis.R

class ShifokorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shifokor, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ShifokorFragment()
    }
}