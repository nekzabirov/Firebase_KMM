@file:OptIn(ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.provider

import com.google.firebase.FIREmailAuthProvider
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
internal actual fun kGetCredential(email: String, password: String): AuthCredential =
    AuthCredentialImpl(FIREmailAuthProvider.credentialWithEmail(email = email, password = password))

internal actual fun kGetCredentialWithLink(email: String, emailLink: String): AuthCredential =
    AuthCredentialImpl(FIREmailAuthProvider.credentialWithEmail(email = email, link = emailLink))