package com.example.tashxis.presentation.ui.auth.preference

import android.content.Context

object PrefHelper {

    private var prefs: TashxisPrefs? = null

    fun getPref(context: Context): TashxisPrefs {
        if (prefs == null) {
            prefs = TashxisPrefsImpl(
                context.getSharedPreferences(
                    TashxisPrefs.PREF_NAME,
                    Context.MODE_PRIVATE
                )
            )
        }
        return prefs!!
    }
}