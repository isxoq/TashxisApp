package uz.tashxis.client.presentation.ui.bottom_nav.navbat_oyna

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import uz.tashxis.client.business.util.lazyFast
import uz.tashxis.client.databinding.FragmentQueueInfoBinding
import uz.tashxis.client.framework.base.BaseFragment
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue.PassQueueData

private const val ARG_PARAM1 = "queueData"

class QueueInfoFragment :
    BaseFragment<FragmentQueueInfoBinding>(FragmentQueueInfoBinding::inflate) {
    private var passQueue: PassQueueData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            passQueue = bundle.getParcelable<Parcelable>("queueData") as PassQueueData?
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViews() {
        with(binding) {
            passQueue.let {
                tvDoctorName.text = it?.doctorName
                tvAge.text = "23"
                tvDoctorSpeciality.text = it?.speciality
                tvDate.text = it?.date
                tvTime.text = it?.time
                tvNameHospital.text = it?.hospitalName
                tvNumber.text = preferences.phone
                tvQueryNumber.text = it?.queueNumber.toString()
                tvSex.text = preferences.gender
                tvFio.text = preferences.let { it->
                     "${it.name} " + "${it.surename} "+ "${it.fathername}"
                }

            }

        }
    }
}
