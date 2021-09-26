package uz.droid.digitalstreet.data.pref

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanPreference(
    val pref: SharedPreferences,
    private val key: String,
    private val defValue: Boolean = false
) : ReadWriteProperty<Any, Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean =
        pref.getBoolean(key, defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        pref.edit()
            .putBoolean(key, value)
            .apply()
    }
}