@file:OptIn(ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.credential

import com.google.firebase.FIRAuthCredential
import kotlinx.cinterop.ExperimentalForeignApi

internal class AuthCredentialImpl internal constructor(internal val credential: FIRAuthCredential): AuthCredential {
    override val provider: String
        get() = credential.provider
    override val signInMethod: String
        get() = credential.provider()
}