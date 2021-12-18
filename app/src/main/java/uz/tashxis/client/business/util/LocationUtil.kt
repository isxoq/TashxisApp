package uz.tashxis.client.business.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import uz.tashxis.client.App
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationUtil {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var location: MutableLiveData<Location> = MutableLiveData()

    // using singleton pattern to get the locationProviderClient
    fun getInstance(appContext: Context): FusedLocationProviderClient {
        if (fusedLocationProviderClient == null)
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(appContext)
        return fusedLocationProviderClient!!
    }

    fun getLocation(): LiveData<Location> {
        if (ActivityCompat.checkSelfPermission(
                uz.tashxis.client.App.context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                uz.tashxis.client.App.context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient!!.lastLocation
                .addOnSuccessListener { loc: Location? ->
                    location.value = loc
                }
            return location
        } else {
            return liveData { }
        }
    }
}
