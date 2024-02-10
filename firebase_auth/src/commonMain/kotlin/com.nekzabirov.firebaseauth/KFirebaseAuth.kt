package com.nekzabirov.firebaseauth

import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.provider.OAuthProvider

/**
 * Interface representing Firebase Authentication functionality.
 * To create platform instance use [getInstance](#getInstance).
 */
interface KFirebaseAuth {
    /**
     * Adds a listener to track authentication state changes.
     * @param listener The listener to add.
     */
    fun addStateDidChangeListener(listener: AuthStateListener)

    /**
     * Removes a listener for authentication state changes.
     * @param listener The listener to remove.
     */
    fun removeAuthStateListener(listener: AuthStateListener)

    /**
     * Sets the language code to be used for localization.
     * @param langCode The language code to set.
     */
    fun setLanguageCode(langCode: String)

    /**
     * Asynchronously creates a new user account with the given email and password.
     * @param email The email address for the new user.
     * @param password The password for the new user.
     */
    suspend fun createUserWithEmailAndPassword(email: String, password: String)

    /**
     * Asynchronously sends a password reset email to the specified email address.
     * @param email The email address to send the reset email to.
     */
    suspend fun sendPasswordResetEmail(email: String)

    /**
     * Asynchronously verifies the password reset code.
     * @param code The password reset code to verify.
     * @return The email address corresponding to the given code.
     */
    suspend fun verifyPasswordResetCode(code: String): String

    /**
     * Asynchronously signs in using an email address and password.
     * @param email The email address for the user.
     * @param password The password for the user.
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String)

    /**
     * Asynchronously signs in anonymously.
     */
    suspend fun signInAnonymously()

    /**
     * Asynchronously signs in using the provided credential.
     * @param credential The credential to sign in with.
     */
    suspend fun signInWithCredential(credential: AuthCredential)

    /**
     * Asynchronously signs in using the provided provider.
     * @param activity The activity context for initiating the sign-in flow.
     * @param provider The OAuth provider to sign in with.
     * [com.nekzabirov.firebaseauth.provider.OAuthProvider]
     */
    suspend fun signInWithProvider(activity: KActivity, provider: OAuthProvider)

    /**
     * Signs out the current user.
     */
    fun signOut()

    /**
     * Companion object providing a singleton instance of KFirebaseAuth.
     */
    companion object {
        /** Singleton instance of KFirebaseAuth. */
        val instance: KFirebaseAuth = getInstance()
    }

    /** Listener interface for authentication state changes. */
    interface AuthStateListener {
        /**
         * Called when the authentication state changes.
         * @param auth The updated KFirebaseAuth instance.
         */
        fun onAuthStateChanged(auth: KFirebaseAuth)
    }
}

/**
 * Retrieves the singleton platform instance of KFirebaseAuth.
 * @return Singleton instance of KFirebaseAuth.
 */
internal expect fun getInstance(): KFirebaseAuth