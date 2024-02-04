package com.nekzabirov.firebaseauth.provider

import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.credential.AuthCredential

interface PhoneAuthProvider {
    suspend fun verifyPhoneNumber(activity: KActivity, phoneNumber: String): String

    fun getCredential(verificationId: String, code: String): AuthCredential
}

expect fun phoneAuthProvider(): PhoneAuthProvider