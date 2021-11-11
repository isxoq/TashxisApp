package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.mapper

import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResLocal
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResponse

interface IAddQueueResMapper {
    fun map(data: AddQueueResponse): AddQueueResLocal
}