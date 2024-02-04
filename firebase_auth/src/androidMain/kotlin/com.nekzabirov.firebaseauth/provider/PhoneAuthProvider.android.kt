package com.nekzabirov.firebaseauth.provider

import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PhoneAuthProviderImpl internal constructor(): PhoneAuthProvider {
    override suspend fun verifyPhoneNumber(activity: KActivity, phoneNumber: String): String = suspendCoroutine { cont ->
        val callbacks = object : com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

            override fun onVerificationFailed(p0: FirebaseException) {}

            override fun onCodeSent(verificationId: String, p1: com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, p1)
                cont.resume(verificationId)
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                cont.resumeWithException(Exception(p0))
            }
        }

        val options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        com.google.firebase.auth.PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun getCredential(verificationId: String, code: String): AuthCredential {
        val cred = com.google.firebase.auth.PhoneAuthProvider.getCredential(verificationId, code)

        return AuthCredentialImpl(cred)
    }
}

actual fun phoneAuthProvider(): PhoneAuthProvider =
    PhoneAuthProviderImpl()