@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.provider

import cocoapods.FirebaseAuth.FIRFacebookAuthProvider
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import kotlinx.cinterop.ExperimentalForeignApi

internal actual fun kGetCredential(accessToken: String): AuthCredential =
    AuthCredentialImpl(FIRFacebookAuthProvider.credentialWithAccessToken(accessToken))