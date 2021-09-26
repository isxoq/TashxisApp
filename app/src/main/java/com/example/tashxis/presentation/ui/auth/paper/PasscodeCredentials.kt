package com.example.tashxis.presentation.ui.auth.paper


import com.example.tashxis.presentation.ui.auth.model.auth.VerifyCodeResponse.Data
import io.paperdb.Paper

object PasscodeCredentials {
    const val auth_tokin_login ="aisjkdhakdhakdhdaskj"

    fun setRegisterCredentials(model: Data) {
        Paper.book().write(auth_tokin_login, model)
    }

}