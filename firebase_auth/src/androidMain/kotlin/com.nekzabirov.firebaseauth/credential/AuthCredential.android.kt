package com.nekzabirov.firebaseauth.credential

class AuthCredentialImpl internal constructor(
    internal val credential: com.google.firebase.auth.AuthCredential
): AuthCredential {
    override val provider: String
        get() = credential.provider
    override val signInMethod: String
        get() = credential.signInMethod
}