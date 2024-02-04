package com.nekzabirov.firebaseauth.user

import com.google.firebase.auth.FirebaseUser
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import kotlinx.coroutines.tasks.await

class KFirebaseUserImpl internal constructor(private val user: FirebaseUser): KFirebaseUser {
    override suspend fun linkWithCredential(credential: AuthCredential) {
        if (credential !is AuthCredentialImpl)
            error("Illegal argument")

        user.linkWithCredential(credential.credential).await()
    }

}