package com.example.tashxis.presentation.ui.auth.preference

import android.content.SharedPreferences
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.BIRTH_DATE
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.FATHERNAME
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.FIREBASE_TOKEN
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.ID
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.ISM
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.IS_ON_BOARD
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.LANGUAGE
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.PHONE
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.PROVINCE_ID
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.REGION_ID
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.SURENAME
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs.Companion.TOKEN

interface TashxisPrefs {
    var birthDate: String?
    var regionId: Int?
    var provinceId: Int?
    var phone: String?
    var id: Int
    var name: String?
    var surename: String?
    var fathername: String?
    var token: String?
    var language: String?
    var firebaseToken: String?
    var isOnBoard: Boolean
    fun clearAll()

    companion object {
        const val BIRTH_DATE = "BIRTHDATE"
        const val REGION_ID = "REGIONID"
        const val PROVINCE_ID = "PROVINCEID"
        const val PHONE = "PHONE"
        const val PREF_NAME = "tashxis.db"
        const val ISM = "ISM"
        const val SURENAME = "SURENAME"
        const val FATHERNAME = "FATHERNAME"
        const val ID = "ID"
        const val TOKEN = "TOKEN"
        const val LANGUAGE = "language"
        const val FIREBASE_TOKEN = "firebase_token"
        const val IS_ON_BOARD = "on_board"
    }
}

class TashxisPrefsImpl(
    private val prefs: SharedPreferences
) : TashxisPrefs {
    override var birthDate: String?
        get() = prefs.getString(BIRTH_DATE, null)
        set(value) = prefs.edit().putString(BIRTH_DATE, value).apply()
    override var regionId: Int?
        get() = prefs.getInt(REGION_ID, 12121)
        set(value) = prefs.edit().putInt(REGION_ID, value!!).apply()
    override var provinceId: Int?
        get() = prefs.getInt(PROVINCE_ID, 2)
        set(value) = prefs.edit().putInt(PROVINCE_ID, value!!).apply()
    override var phone: String?
        get() = prefs.getString(PHONE, "")
        set(value) = prefs.edit().putString(PHONE, value).apply()
    override var id: Int
        get() = prefs.getInt(ID, 12121)
        set(value) = prefs.edit().putInt(ID, value).apply()
    override var name: String?
        get() = prefs.getString(ISM, null)
        set(value) = prefs.edit().putString(ISM, value).apply()
    override var surename: String?
        get() = prefs.getString(SURENAME, null)
        set(value) = prefs.edit().putString(SURENAME, value).apply()
    override var fathername: String?
        get() = prefs.getString(FATHERNAME, null)
        set(value) = prefs.edit().putString(FATHERNAME, value).apply()
    override var token: String?
        get() = prefs.getString(TOKEN, null)
        set(value) {
            prefs.edit().putString(TOKEN, value).apply()
            /*if (value == null) {
                broadcastManager.sendBroadcast(
                    Intent(BroadCast.LOG_OUT.name)
                )
            }*/
        }

    override var language: String?
        get() = prefs.getString(LANGUAGE, null)
        set(value) = prefs.edit().putString(LANGUAGE, value).apply()

    override var firebaseToken: String?
        get() = prefs.getString(FIREBASE_TOKEN, null)
        set(value) = prefs.edit().putString(FIREBASE_TOKEN, value).apply()

    override var isOnBoard: Boolean
        get() = prefs.getBoolean(IS_ON_BOARD, true)
        set(value) = prefs.edit().putBoolean(IS_ON_BOARD, value).apply()

    override fun clearAll() {
        val lang = language
        prefs.edit().clear().apply()
        language = lang
    }

}