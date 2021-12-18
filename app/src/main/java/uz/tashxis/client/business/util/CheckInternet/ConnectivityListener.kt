package uz.tashxis.client.business.util.CheckInternet

/**
 * Created by Govind on 1/19/2018.
 */
interface ConnectivityListener {
    fun onNetworkConnectionChanged(isConnected: Boolean)
}