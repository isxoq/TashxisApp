package com.example.tashxis.ui.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.tashxis.databinding.FragmentRoyxatdanOtishBinding

class RoyxatdanOtishFragment : Fragment() {

    lateinit var binding: FragmentRoyxatdanOtishBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoyxatdanOtishBinding.inflate(inflater,container,false)
    return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // binding = FragmentRoyxatdanOtishBinding.bind(view)

        val viloyatlar = arrayOf("Namangan" , "Andijon" , "Farg'ona" , "Toshkent")

       /* binding.spinnerVil.adapter = ArrayAdapter(requireContext() , android.R.layout.simple_list_item_1 , viloyatlar)

        binding.spinnerVil.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

*/    }

}