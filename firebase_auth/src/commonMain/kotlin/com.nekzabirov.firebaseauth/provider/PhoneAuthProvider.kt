package com.nekzabirov.firebaseauth.provider

import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.PhoneAuthCredential

/**
 * Interface representing phone number authentication provider.
 * This can be used for verifying phone numbers and generating authentication credentials.
 */
interface PhoneAuthProvider {
    /**
     * Suspends the coroutine until the phone number verification process is completed.
     *
     * @param activity The activity context where verification process will be initiated.
     * @param phoneNumber The phone number to be verified.
     * @return A verification ID string that can be used to generate an authentication credential.
     */
    suspend fun verifyPhoneNumber(activity: KActivity, phoneNumber: String): String

    /**
     * Generates an [AuthCredential](#AuthCredential) using the verification ID and verification code.
     *
     * @param verificationId The verification ID obtained from the phone number verification process.
     * @param code The verification code entered by the user.
     * @return An [PhoneAuthCredential] object representing phone number authentication.
     */
    fun getCredential(verificationId: String, code: String): PhoneAuthCredential
}

/**
 * Factory function for obtaining a platform-specific implementation of [PhoneAuthProvider].
 * This function should be implemented separately for each platform.
 */
expect fun phoneAuthProvider(): PhoneAuthProvider