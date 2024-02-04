package com.nekzabirov.firebaseauth.provider

import com.nekzabirov.firebaseauth.credential.AuthCredential

object FacebookAuthProvider {
    fun getCredential(accessToken: String): AuthCredential =
        kGetCredential(accessToken)
}

internal expect fun kGetCredential(accessToken: String): AuthCredential