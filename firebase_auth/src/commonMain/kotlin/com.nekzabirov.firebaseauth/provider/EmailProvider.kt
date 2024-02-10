package com.nekzabirov.firebaseauth.provider

import com.nekzabirov.firebaseauth.credential.AuthCredential

/**
 * Object providing utility functions for creating AuthCredentials using email-based authentication.
 */
object EmailProvider {
    /**
     * @param email The user's email address.
     * @param password The user's password.
     * @return [AuthCredential] object for email/password authentication
     */
    fun getCredential(email: String, password: String): AuthCredential =
        kGetCredential(email, password)

    /**
     * @param email The user's email address.
     * @param emailLink The link received via email for passwordless authentication.
     * @return [AuthCredential] object for email/link authentication
     */
    fun getCredentialWithLink(email: String, emailLink: String): AuthCredential =
        kGetCredentialWithLink(email, emailLink)
}

internal expect fun kGetCredential(email: String, password: String): AuthCredential

internal expect fun kGetCredentialWithLink(email: String, emailLink: String): AuthCredential