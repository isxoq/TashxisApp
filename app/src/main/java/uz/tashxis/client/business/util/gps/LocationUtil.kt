package uz.tashxis.client.business.util.gps

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient

class LocationUtil(private val context: Context) {

    private val settingsClient: SettingsClient = LocationServices.getSettingsClient(context)
    private val locationSettingsRequest: LocationSettingsRequest?
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    /**
     *
     */
    init {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationLiveData.locationRequest)
        locationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)
    }

    /**
     *
     */
    fun turnGPSOn(listener: OnLocationOnListener?) {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            listener?.locationStatus(true)
            println("my_tag -> GPS_PROVIDER is enabled")
        } else {
            settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(context as Activity) {
                    // GPS enabled already
                    println("my_tag -> addOnSuccessListener")
                    listener?.locationStatus(true)
                }
                .addOnFailureListener(context) { e ->
                    println("my_tag -> addOnSuccessListener")
                    listener?.locationStatus(false)
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                            try {
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(
                                    context,
                                    LOCATION_PERMISSION_REQUEST
                                )
                            } catch (sie: IntentSender.SendIntentException) {
                                Log.i("TAG", "PendingIntent unable to execute request.")
                            }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage =
                                "Enable location services from settings."
                            Log.e("TAG", errorMessage)

                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }

    /**
     *
     */
    interface OnLocationOnListener {
        fun locationStatus(isLocationOn: Boolean)
    }
}

const val LOCATION_PERMISSION_REQUEST = 1001