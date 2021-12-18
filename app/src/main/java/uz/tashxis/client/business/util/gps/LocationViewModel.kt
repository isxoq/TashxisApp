package uz.tashxis.client.business.util.gps

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uz.tashxis.client.business.util.gps.LocationLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * MutableLiveData private field to get/save location updated values
     */
    private val locationData =
        LocationLiveData(application)

    /**
     * LiveData a public field to observe the changes of location
     */
    val getLocationData: LiveData<Location> = locationData
}