package com.example.tashxis.ui.auth.paper
import com.example.tashxis.ui.auth.model.EditUserModel
import io.paperdb.Paper

object UserCredentials {
    const val login = "jdhwkdgretjha"
    fun setUserCredentials(model: EditUserModel) {
        Paper.book().write(login, model)
    }
    fun UserCredentials(): EditUserModel {
        return Paper.book().read(login, EditUserModel())
    }
    fun deleteUserCredentials() {
        Paper.book().delete(login)
    }
}