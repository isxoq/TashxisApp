package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.queue

import android.os.Parcel
import android.os.Parcelable

data class PassQueueData(
    var clientId: Long = 0L,
    var doctorId: Long = 0L,
    var doctorName: String = "",
    var speciality: String = "",
    var imageUrl: String = "",
    var date: String = "",
    var time: String = "",
    var hospitalName: String = "",
    var patientPhone: String = "",
    var queueNumber: Long = 0L
) : Parcelable {

    constructor(parcel: Parcel) : this() {
        clientId = parcel.readLong()
        queueNumber = parcel.readLong()
        doctorId = parcel.readLong()
        doctorName = parcel.readString().toString()
        speciality = parcel.readString().toString()
        imageUrl = parcel.readString().toString()
        date = parcel.readString().toString()
        time = parcel.readString().toString()
        hospitalName = parcel.readString().toString()
        patientPhone = parcel.readString().toString()

    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(clientId)
        parcel.writeLong(queueNumber)
        parcel.writeLong(doctorId)
        parcel.writeString(doctorName)
        parcel.writeString(speciality)
        parcel.writeString(imageUrl)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(hospitalName)
        parcel.writeString(patientPhone)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PassQueueData> {
        override fun createFromParcel(parcel: Parcel): PassQueueData {
            return PassQueueData(parcel)
        }

        override fun newArray(size: Int): Array<PassQueueData?> {
            return arrayOfNulls(size)
        }
    }


}