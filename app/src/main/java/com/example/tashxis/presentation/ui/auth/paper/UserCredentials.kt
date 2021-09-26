package com.example.tashxis.presentation.ui.auth.paper



import com.example.tashxis.presentation.ui.auth.model.auth.login_verify.Data
import io.paperdb.Paper

object UserCredentials {
    const val login = "jdhwkdgretjha"


    fun setUserCredentials(model: Data) {
        Paper.book().write(login, model)
    }

    fun deleteUserCredentials() {
        Paper.book().delete(login)
    }
}