package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue


import com.google.firebase.database.PropertyName
data class Queue(
    @get:PropertyName("client_id")
    @set:PropertyName("client_id")
    var clientId: Long? = null,
    @get:PropertyName("confirmed_by_reception")
    @set:PropertyName("confirmed_by_reception")
    var confirmedByReception: Boolean? = null,
    @get:PropertyName("date")
    @set:PropertyName("date")
    var date: String? = null,
    @get:PropertyName("doctor")
    @set:PropertyName("doctor")
    var doctor: Doctor? = null,
    @get:PropertyName("father_name")
    @set:PropertyName("father_name")
    var fatherName: String? = null,
    @get:PropertyName("first_name")
    @set:PropertyName("first_name")
    var firstName: String? = null,
    @get:PropertyName("last_name")
    @set:PropertyName("last_name")
    var lastName: String? = null,
    @get:PropertyName("phone")
    @set:PropertyName("phone")
    var phone: String? = null,
    @get:PropertyName("queue_number")
    @set:PropertyName("queue_number")
    var queueNumber: Int? = null,
    @get:PropertyName("status")
    @set:PropertyName("status")
    var status: Int? = null,
    @get:PropertyName("time")
    @set:PropertyName("time")
    var time: String? = null,
    @get:PropertyName("type")
    @set:PropertyName("type")
    var type: Int? = null
)