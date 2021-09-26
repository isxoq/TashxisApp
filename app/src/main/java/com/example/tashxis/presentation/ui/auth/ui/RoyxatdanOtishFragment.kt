package com.example.tashxis.presentation.ui.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.Application
import com.example.tashxis.business.util.Status
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentRoyxatdanOtishBinding
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.framework.viewModel.AuthViewModel
import com.example.tashxis.framework.viewModel.AuthViewModelFactory
import com.example.tashxis.presentation.ui.activity.MainActivity
import com.example.tashxis.presentation.ui.auth.model.auth.RegionResponse.Data

class RoyxatdanOtishFragment : Fragment(),
AdapterView.OnItemSelectedListener{

    private var _binding: FragmentRoyxatdanOtishBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private val binding get() = _binding!!
    private val TAG = "TAG"
    lateinit var regions: List<Data>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = RetrofitClient.instance
        authViewModel = ViewModelProvider(
            requireActivity(),
            AuthViewModelFactory(
                Application(),
                AuthRepository(api)
            )
        )[AuthViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoyxatdanOtishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // binding = FragmentRoyxatdanOtishBinding.bind(view)
        authViewModel.liveState.observe(viewLifecycleOwner, {
            when (it) {
                Status.LOADING -> {
                    Log.d(TAG, "AddRegisterInfo onViewCreated: Loading")
                }
                Status.ERROR -> {
                    Log.d(TAG, "AddRegisterInfo onViewCreated: Error")
                }
                Status.SUCCESS -> {
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity?.startActivity(intent)
                }
            }
        })
        authViewModel.regionList.observe(viewLifecycleOwner, {
            var list: MutableList<String> = mutableListOf()

            val dataRegion = it as List<Data>
            regions = dataRegion
            for (i in 0..regions.size) {
                list[i] = regions[i].name
            }

        })
        var list1 = mutableListOf<String>("1","2","3")
        binding.spinnerRegion.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list1)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}