@file:OptIn(ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.credential

import cocoapods.FirebaseAuth.FIRPhoneAuthCredential
import kotlinx.cinterop.ExperimentalForeignApi

internal class PhoneAuthCredentialImpl internal constructor(internal val credential: FIRPhoneAuthCredential): PhoneAuthCredential {
    override val provider: String
        get() = credential.provider
    override val signInMethod: String
        get() = credential.provider()
}