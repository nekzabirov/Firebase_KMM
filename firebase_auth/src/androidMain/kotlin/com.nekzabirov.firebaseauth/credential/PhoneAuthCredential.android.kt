package com.nekzabirov.firebaseauth.credential

class PhoneAuthCredentialImpl internal constructor(
    internal val credential: com.google.firebase.auth.PhoneAuthCredential
) : PhoneAuthCredential {
    override val provider: String
        get() = credential.provider
    override val signInMethod: String
        get() = credential.signInMethod
}