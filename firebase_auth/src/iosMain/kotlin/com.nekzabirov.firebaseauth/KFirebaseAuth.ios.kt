package com.nekzabirov.firebaseauth

import cocoapods.FirebaseAuth.FIRAuth
import cocoapods.FirebaseAuth.FIROAuthProvider
import com.nekzabirov.firebaseapp.KActivity
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.nekzabirov.firebaseapp.ex.toKotlin
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import com.nekzabirov.firebaseauth.provider.OAuthProvider
import com.nekzabirov.firebaseauth.user.KFirebaseUser
import com.nekzabirov.firebaseauth.user.KFirebaseUserImpl
import kotlinx.cinterop.ExperimentalForeignApi

@ExperimentalForeignApi
class KFirebaseAuthImpl internal constructor(private val firebaseAuth: FIRAuth): KFirebaseAuth {
    private val stateListeners = arrayListOf<KFirebaseAuth.AuthStateListener>()

    override val currentUser: KFirebaseUser?
        get() = firebaseAuth.currentUser?.let { KFirebaseUserImpl(it) }

    init {
        firebaseAuth.addAuthStateDidChangeListener { _, _ ->
            stateListeners.forEach { it.onAuthStateChanged(this) }
        }
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

    override suspend fun createUserWithEmailAndPassword(email: String, password: String) = suspendCoroutine { cont ->
        firebaseAuth.createUserWithEmail(email = email, password = password) { _, error ->
            if (error == null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun sendPasswordResetEmail(email: String) = suspendCoroutine { cont ->
        firebaseAuth.sendPasswordResetWithEmail(email) { error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun verifyPasswordResetCode(code: String): String = suspendCoroutine { cont ->
        firebaseAuth.verifyPasswordResetCode(code = code) { result, error ->
            if (result != null) {
                cont.resume(result)
            } else {
                cont.resumeWithException(error.toKotlin())
            }
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) = suspendCoroutine { cont ->
        firebaseAuth.signInWithEmail(email = email, password = password) { _, error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun signInAnonymously() = suspendCoroutine { cont ->
        firebaseAuth.signInAnonymouslyWithCompletion { _, error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun signInWithCredential(credential: AuthCredential) = suspendCoroutine { cont ->
        if (credential !is AuthCredentialImpl)
            error("Illegal argument")

        firebaseAuth.signInWithCredential(credential.credential) { _, error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun signInWithProvider(activity: KActivity, provider: OAuthProvider) = suspendCoroutine { cont ->
        val platformProvider = FIROAuthProvider.providerWithProviderID(provider.providerID).apply {
            setCustomParameters(provider.customParameters as Map<Any?, *>)
            setScopes(provider.scopes)
        }

        platformProvider.getCredentialWithUIDelegate(null) { firAuthCredential, error ->
            if (error != null || firAuthCredential == null) {
                cont.resumeWithException(error.toKotlin())
                return@getCredentialWithUIDelegate
            }

            firebaseAuth.signInWithCredential(firAuthCredential) { _, er ->
                if (er != null) {
                    cont.resumeWithException(er.toKotlin())
                } else {
                    cont.resume(Unit)
                }
            }
        }
    }

    override fun signOut() {
        firebaseAuth.signOut(null)
    }

    @OptIn(ExperimentalForeignApi::class)
    fun canHandleURL(url: platform.Foundation.NSURL) =
        firebaseAuth.canHandleURL(url)

    fun canHandleNotification(userInfo: Map<Any?, *>) =
        firebaseAuth.canHandleNotification(userInfo)
}

@ExperimentalForeignApi
internal actual fun getInstance(): KFirebaseAuth =
    KFirebaseAuthImpl(FIRAuth.auth())