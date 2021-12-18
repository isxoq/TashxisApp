package uz.tashxis.client.business.util

sealed class NetworkStatus<T> {
    class LOADING<T> : NetworkStatus<T>()
    class SUCCESS<T>(val data: T) : NetworkStatus<T>()
    class ERROR<T>(val error: Any) : NetworkStatus<T>()
}