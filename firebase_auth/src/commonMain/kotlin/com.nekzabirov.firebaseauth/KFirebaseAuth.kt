package com.nekzabirov.firebaseauth

import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.provider.OAuthProvider

interface KFirebaseAuth {
    fun addStateDidChangeListener(listener: AuthStateListener)

    fun removeAuthStateListener(listener: AuthStateListener)

    fun setLanguageCode(langCode: String)

    suspend fun createUserWithEmailAndPassword(email: String, password: String)

    suspend fun sendPasswordResetEmail(email: String)

    suspend fun verifyPasswordResetCode(code: String): String

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    suspend fun signInAnonymously()

    suspend fun signInWithCredential(credential: AuthCredential)

    suspend fun signInWithProvider(activity: KActivity, provider: OAuthProvider)

    fun signOut()

    companion object {
        val instance: KFirebaseAuth = getInstance()
    }

    interface AuthStateListener {
        fun onAuthStateChanged(var1: KFirebaseAuth)
    }
}

internal expect fun getInstance(): KFirebaseAuth