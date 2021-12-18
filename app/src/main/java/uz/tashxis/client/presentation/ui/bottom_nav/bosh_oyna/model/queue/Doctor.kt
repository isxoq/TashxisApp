package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue


import com.google.firebase.database.PropertyName

data class Doctor(
    @get:PropertyName("address")
    @set:PropertyName("address")
    var address: String? = null,
    @get:PropertyName("first_name")
    @set:PropertyName("first_name")
    var firstName: String? = null,
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: Long? = null,
    @get:PropertyName("imageurl")
    @set:PropertyName("imageurl")
    var imageUrl: String? = null,
    @get:PropertyName("last_name")
    @set:PropertyName("last_name")
    var lastName: String? = null,
    @get:PropertyName("speciality")
    @set:PropertyName("speciality")
    var speciality: String? = null
)