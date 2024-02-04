package com.nekzabirov.firebaseauth.provider

import com.nekzabirov.firebaseauth.credential.AuthCredential

object EmailProvider {
    fun getCredential(email: String, password: String): AuthCredential =
        kGetCredential(email, password)

    fun getCredentialWithLink(email: String, emailLink: String): AuthCredential =
        kGetCredentialWithLink(email, emailLink)
}

internal expect fun kGetCredential(email: String, password: String): AuthCredential

internal expect fun kGetCredentialWithLink(email: String, emailLink: String): AuthCredential