package com.nekzabirov.firebaseauth

import com.nekzabirov.firebaseauth.user.KFireUser

expect class KFireAuth private constructor() {
    inner class Phone internal constructor() {
        /**
         * @return verificationID after success send code
         * @throws com.nekzabirov.firebaseapp.AuthFail
         **/
        suspend fun verifyPhoneNumber(phoneNumber: String): String

        suspend fun getCredential(verificationID: String, verificationCode: String): KFireCredential
    }

    inner class Google internal constructor() {
        suspend fun getCredential(idToken: String): KFireCredential
    }

    inner class Facebook internal constructor() {
        suspend fun getCredential(accessToken: String): KFireCredential
    }

    companion object {
        fun getInstance(): KFireAuth
    }

    val currentUser: KFireUser?

    val phone: Phone

    val google: Google

    val facebook: Facebook

    suspend fun signInWithCredential(credential: KFireCredential): Boolean
}

expect abstract class KFireCredential