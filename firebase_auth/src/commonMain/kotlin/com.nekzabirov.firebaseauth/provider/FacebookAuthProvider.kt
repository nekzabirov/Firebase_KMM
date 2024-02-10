package com.nekzabirov.firebaseauth.provider

import com.nekzabirov.firebaseauth.credential.AuthCredential

/**
 * Object providing utility functions for creating AuthCredentials using Facebook authentication.
 */
object FacebookAuthProvider {
    /**
     * @param accessToken The access token obtained from Facebook SDK after successful user sign-in.
     * @return An [AuthCredential] object for Facebook authentication.
     */
    fun getCredential(accessToken: String): AuthCredential =
        kGetCredential(accessToken)
}

internal expect fun kGetCredential(accessToken: String): AuthCredential