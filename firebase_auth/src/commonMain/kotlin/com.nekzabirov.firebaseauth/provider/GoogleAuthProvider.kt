package com.nekzabirov.firebaseauth.provider

import com.nekzabirov.firebaseauth.credential.AuthCredential

object GoogleAuthProvider {
    fun getCredential(idToken: String?, accessToken: String?): AuthCredential =
        kGetCredential(idToken, accessToken)
}

internal expect fun kGetCredential(idToken: String?, accessToken: String?): AuthCredential