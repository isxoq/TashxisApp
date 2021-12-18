package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import uz.tashxis.client.R
import uz.tashxis.client.business.util.NetworkStatus
import uz.tashxis.client.data.RetrofitClient
import uz.tashxis.client.databinding.FragmentSpecialityBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.framework.repo.MainRepository
import uz.tashxis.client.framework.viewModel.SpecialityViewModel
import uz.tashxis.client.framework.viewModel.SpecialityViewModelFactory
import uz.tashxis.client.presentation.adapters.SpecialityAdapter
import uz.tashxis.client.presentation.adapters.SpecialityClickListener
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.speciality.SpecialData


class SpecialityFragment :
    BaseFragment<FragmentSpecialityBinding>(FragmentSpecialityBinding::inflate),
    SpecialityClickListener {
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private val specialityObserver = Observer<NetworkStatus<List<SpecialData>>> {
        when (it) {
            is NetworkStatus.LOADING -> {
                showProgress()
                Log.i("NetworkStatus", "Loading: ${it}")
            }
            is NetworkStatus.SUCCESS -> {
                hideProgress()
                Log.i("NetworkStatus", "Succes: ${it.data}")
                sAdapter.submitList(it.data)
            }
            is NetworkStatus.ERROR -> {
                hideProgress()
                Log.i("NetworkStatus", "Error: ${it.error}")
            }
        }
    }

    private fun setupObserver() {
        viewModel.liveSpecialityState.observe(viewLifecycleOwner, specialityObserver)
    }

    private fun setupView() {

        sAdapter.itemClickListener(this)
        binding.rvSpeciality.adapter = sAdapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSpeciality.layoutManager = layoutManager
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen._8sdp)
        binding.rvSpeciality.addItemDecoration(
            uz.tashxis.client.business.util.GridSpacingItemDecoration(
                2,
                spacingInPixels,
                true,
                0
            )
        )


    }

    override fun onClicked(model: SpecialData) {
//        Toast.makeText(requireActivity(), "${model.description}", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("id" to model.id, "title" to model.name)
        findNavController().navigate(R.id.action_shifokorFragment_to_doctorsFragment, bundle)
    }

}