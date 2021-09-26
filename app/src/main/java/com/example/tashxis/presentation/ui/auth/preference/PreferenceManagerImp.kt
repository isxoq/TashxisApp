package info.texnoman.texnomart.auth.preference

import android.content.SharedPreferences
import com.example.tashxis.business.util.Constants
import uz.droid.digitalstreet.data.pref.IntPreference


class PreferenceManagerImp(pref: SharedPreferences) : IPreferenceManager {
    override var authToken: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var birthDate: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var createdAt: Int
            by IntPreference(pref, Constants.PREF_NAME)
    override var email: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var fatherName: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var firstName: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var gender: Int
            by IntPreference(pref, Constants.PREF_NAME)
    override var hospitalId: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var id: Int
            by IntPreference(pref, Constants.PREF_NAME)
    override var image: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var lastName: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var passwordHash: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var passwordResetToken: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var phone: String
            by StringPreference(pref, Constants.PREF_NAME)
    override var provinceId: Int
            by IntPreference(pref, Constants.PREF_NAME)
    override var regionId: Int
            by IntPreference(pref, Constants.PREF_NAME)
    override var status: Int
            by IntPreference(pref, Constants.PREF_NAME)
    override var typeId: Int
            by IntPreference(pref, Constants.PREF_NAME)
    override var updatedAt: Int
            by IntPreference(pref, Constants.PREF_NAME)
    override var username: String
            by StringPreference(pref, Constants.PREF_NAME)
}