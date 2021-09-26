package uz.droid.digitalstreet.data.pref

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class IntPreference(
    private val pref: SharedPreferences,
    private val key: String,
    private val defValue: Int = 0
) : ReadWriteProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Int = pref.getInt(key, defValue) ?: defValue

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        pref.edit()
            .putInt(key, value)
            .apply()
    }
}