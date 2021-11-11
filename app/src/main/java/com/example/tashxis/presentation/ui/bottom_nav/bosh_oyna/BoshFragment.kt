package com.example.tashxis.presentation.ui.bottom_nav.bosh_oyna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tashxis.R
import com.example.tashxis.databinding.FragmentBoshBinding
import com.example.tashxis.presentation.ui.activity.IFullScreen

class BoshFragment : Fragment() {

    private lateinit var binding: FragmentBoshBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bosh, container, false)
    }

}