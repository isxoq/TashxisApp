package uz.droid.digitalstreet.data.pref

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FloatPreference(
    private val pref: SharedPreferences,
    private val key: String,
    private val defValue: Float = -1f
) : ReadWriteProperty<Any, Float> {

    override fun getValue(thisRef: Any, property: KProperty<*>) =
        pref.getFloat(key, defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
        pref.edit()
            .putFloat(key, value)
            .apply()

    }
}