package com.example.tashxis.ui.navbat_oyna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tashxis.R

class NavbatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navbat, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = NavbatFragment()
    }
}