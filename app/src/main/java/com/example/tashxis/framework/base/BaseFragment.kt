package com.example.tashxis.framework.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.tashxis.R

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private var dialog: AlertDialog? = null
    private val progressDialog: AlertDialog?
        get() {
            if (dialog == null)
                context?.let {
                    dialog = AlertDialog.Builder(it, R.style.WrapContentDialog)
                        .setView(R.layout.progress_dialog)
                        .create()
                }
            return dialog
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated()
    }

    protected open fun viewCreated() {}

    protected fun showProgress() {
        progressDialog?.show()
    }

    protected fun hideProgress() {
        progressDialog?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}