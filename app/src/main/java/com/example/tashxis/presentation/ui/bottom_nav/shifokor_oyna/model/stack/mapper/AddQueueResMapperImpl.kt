package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.mapper

import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResLocal
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResponse

class AddQueueResMapperImpl : IAddQueueResMapper {
    override fun map(data: AddQueueResponse): AddQueueResLocal {
        return AddQueueResLocal(
            id = data.id,
            doctorImage = data.doctorImage,
            doctorFirstName = data.doctorFirstName,
            doctorLastName = data.doctorLastName,
            doctorSpeciality = data.doctorSpeciality,
            doctorAddress = data.doctorAddress,
            date = data.date,
            time = data.time
        )
    }

}