package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue

import com.google.firebase.database.PropertyName

data class Queues(
    @get:PropertyName("queues")
    @set:PropertyName("queues")
    var queues: Map<String?, Queue?>? = null
)
