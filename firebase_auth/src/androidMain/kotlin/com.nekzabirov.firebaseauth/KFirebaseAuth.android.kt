package com.nekzabirov.firebaseauth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import kotlinx.coroutines.tasks.await

class KFirebaseAuthImpl internal constructor(private val firebaseAuth: FirebaseAuth): KFirebaseAuth {
    private val stateListeners = arrayListOf<KFirebaseAuth.AuthStateListener>()

    init {
        firebaseAuth.addAuthStateListener { _ -> stateListeners.forEach { it.onAuthStateChanged(this) } }
    }

    override fun addStateDidChangeListener(listener: KFirebaseAuth.AuthStateListener) {
        stateListeners.add(listener)
    }

    override fun removeAuthStateListener(listener: KFirebaseAuth.AuthStateListener) {
        stateListeners.remove(listener)
    }

    override fun setLanguageCode(langCode: String) {
        firebaseAuth.setLanguageCode(langCode)
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun verifyPasswordResetCode(code: String): String {
        return firebaseAuth.verifyPasswordResetCode(code).await()
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInAnonymously() {
        firebaseAuth.signInAnonymously().await()
    }

    override suspend fun signInWithCredential(credential: AuthCredential) {
        if (credential !is AuthCredentialImpl) {
            error("Illegal argument")
        }

        firebaseAuth.signInWithCredential(credential.credential).await()
    }

    override suspend fun signInWithProvider(
        activity: KActivity,
        provider: com.nekzabirov.firebaseauth.provider.OAuthProvider
    ) {
        val platformProvider = OAuthProvider.newBuilder(provider.providerID)
            .addCustomParameters(provider.customParameters)
            .setScopes(provider.scopes)
            .build()

        firebaseAuth
            .startActivityForSignInWithProvider(activity, platformProvider)
            .await()
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}

internal actual fun getInstance(): KFirebaseAuth =
    KFirebaseAuthImpl(FirebaseAuth.getInstance())