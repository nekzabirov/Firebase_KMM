@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.user

import com.google.firebase.FIRUser
import com.nekzabirov.firebaseapp.ex.toKotlin
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KFirebaseUserImpl internal constructor(private val user: FIRUser): KFirebaseUser {
    override suspend fun linkWithCredential(credential: AuthCredential) = suspendCoroutine { cont ->
        if (credential !is AuthCredentialImpl)
            error("Illegal argument")

        user.linkWithCredential(credential.credential) { _, error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

}