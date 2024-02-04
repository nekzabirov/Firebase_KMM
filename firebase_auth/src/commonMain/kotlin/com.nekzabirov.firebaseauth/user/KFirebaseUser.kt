package com.nekzabirov.firebaseauth.user

import com.nekzabirov.firebaseauth.credential.AuthCredential

interface KFirebaseUser {
    suspend fun linkWithCredential(credential: AuthCredential)
}