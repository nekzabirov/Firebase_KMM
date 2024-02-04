@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.provider

import cocoapods.FirebaseAuth.FIRGoogleAuthProvider
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import kotlinx.cinterop.ExperimentalForeignApi

internal actual fun kGetCredential(idToken: String?, accessToken: String?): AuthCredential =
    AuthCredentialImpl(FIRGoogleAuthProvider.credentialWithIDToken(idToken!!, accessToken!!))