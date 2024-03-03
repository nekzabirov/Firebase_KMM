@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class, ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.provider

import cocoapods.FirebaseAuth.FIRPhoneAuthProvider
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseapp.ex.toKotlin
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import com.nekzabirov.firebaseauth.credential.PhoneAuthCredentialImpl
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

    override fun getCredential(verificationId: String, code: String): com.nekzabirov.firebaseauth.credential.PhoneAuthCredential {
        val cred = FIRPhoneAuthProvider.provider().credentialWithVerificationID(verificationId, code)

        return PhoneAuthCredentialImpl(cred)
    }

}

actual fun phoneAuthProvider(): PhoneAuthProvider =
    PhoneAuthProviderImpl()