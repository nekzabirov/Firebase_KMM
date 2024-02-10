package com.nekzabirov.firebaseauth.provider

import com.nekzabirov.firebaseauth.credential.AuthCredential

/**
 * Object providing utility functions for creating [AuthCredential] using Google authentication.
 */
object GoogleAuthProvider {
    /**
     * @param idToken The ID token obtained from Google sign-in.
     * @param accessToken The access token obtained from Google sign-in.
     * @return An [AuthCredential] object for Google authentication.
     */
    fun getCredential(idToken: String?, accessToken: String?): AuthCredential =
        kGetCredential(idToken, accessToken)
}

internal expect fun kGetCredential(idToken: String?, accessToken: String?): AuthCredential