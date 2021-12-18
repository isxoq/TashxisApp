package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.mapper

import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResLocal
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResponse

interface IAddQueueResMapper {
    fun map(data: AddQueueResponse): AddQueueResLocal
}