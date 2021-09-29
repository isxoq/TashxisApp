package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tashxis.R
import com.example.tashxis.business.util.GridSpacingItemDecoration
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.progressDialog
import com.example.tashxis.data.RetrofitClient
import com.example.tashxis.databinding.FragmentSpecialityBinding
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewModel.SpecialityViewModel
import com.example.tashxis.framework.viewModel.SpecialityViewModelFactory
import com.example.tashxis.presentation.adapters.SpecialityAdapter
import com.example.tashxis.presentation.adapters.SpecialityClickListener
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.speciality_response.SpecialityData


class SpecialityFragment : Fragment(R.layout.fragment_speciality), SpecialityClickListener {
    private var _binding: FragmentSpecialityBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SpecialityViewModel
    private val sAdapter: SpecialityAdapter by lazy { SpecialityAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitClient.instance
        viewModel = ViewModelProvider(
            requireActivity(),
            SpecialityViewModelFactory(
                requireActivity().application,
                MainRepository(api)
            )
        )[SpecialityViewModel::class.java]

        viewModel.getSpeciality()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpecialityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private val specialityObserver = Observer<NetworkStatus<List<SpecialityData>>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                progressDialog?.show()
                Log.i("NetworkStatus", "Loading: ${it}")
            }
            is NetworkStatus.SUCCESS -> {
                progressDialog?.hide()
                Log.i("NetworkStatus", "Succes: ${it.data}")
                sAdapter.submitList(it.data)
            }
            is NetworkStatus.ERROR -> {
                progressDialog?.hide()
                Log.i("NetworkStatus", "Error: ${it.error}")
            }
        }
    }

    private fun setupObserver() {
        viewModel.liveSpecialityState.observe(viewLifecycleOwner, specialityObserver)
    }

    private fun setupView() {

        sAdapter.itemClickListener(this)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSpeciality.layoutManager = layoutManager
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen._8sdp)
        binding.rvSpeciality.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                spacingInPixels,
                true,
                0
            )
        )
        binding.rvSpeciality.adapter = sAdapter

    }

    override fun onClicked(model: SpecialityData) {
//        Toast.makeText(requireActivity(), "${model.description}", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("id" to model.id)
        findNavController().navigate(R.id.action_shifokorFragment_to_doctorsFragment, bundle)
    }
}