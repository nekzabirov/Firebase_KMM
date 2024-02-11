@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class, ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.provider

import com.google.firebase.FIRPhoneAuthProvider
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseapp.ex.toKotlin
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PhoneAuthProviderImpl internal constructor(): PhoneAuthProvider {
    override suspend fun verifyPhoneNumber(activity: KActivity, phoneNumber: String): String = suspendCoroutine { cont ->
        FIRPhoneAuthProvider.provider().verifyPhoneNumber(phoneNumber, null) { verificationId, error ->
            if (verificationId != null) {
                cont.resume(verificationId)
            } else {
                cont.resumeWithException(error.toKotlin())
            }
        }
    }

    override fun getCredential(verificationId: String, code: String): AuthCredential {
        val cred = FIRPhoneAuthProvider.provider().credentialWithVerificationID(verificationId, code)

        return AuthCredentialImpl(cred)
    }

}

actual fun phoneAuthProvider(): PhoneAuthProvider =
    PhoneAuthProviderImpl()