package com.example.tashxis.ui.bosh_oyna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tashxis.R
import com.example.tashxis.databinding.FragmentBoshBinding

class BoshFragment : Fragment() {

    private lateinit var binding: FragmentBoshBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bosh, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = BoshFragment()
    }
}