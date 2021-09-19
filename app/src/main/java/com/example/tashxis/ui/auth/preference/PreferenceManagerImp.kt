package info.texnoman.texnomart.auth.preference

import android.content.SharedPreferences
import com.example.tashxis.util.Constants


class PreferenceManagerImp(pref: SharedPreferences): IPreferenceManager {
    override var authToken: String
        by StringPreference(pref, Constants.PREF_NAME)
}