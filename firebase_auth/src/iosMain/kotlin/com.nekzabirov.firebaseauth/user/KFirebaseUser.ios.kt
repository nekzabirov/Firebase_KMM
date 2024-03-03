@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class)

package com.nekzabirov.firebaseauth.user

import cocoapods.FirebaseAuth.FIROAuthProvider
import cocoapods.FirebaseAuth.FIRUser
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseapp.ex.toKotlin
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import com.nekzabirov.firebaseauth.credential.PhoneAuthCredential
import com.nekzabirov.firebaseauth.credential.PhoneAuthCredentialImpl
import com.nekzabirov.firebaseauth.provider.OAuthProvider
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.timeIntervalSince1970
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KFirebaseUserImpl internal constructor(private val user: FIRUser) : KFirebaseUser {
    override val photoUrl: String?
        get() = user.photoURL?.toString()

    override val displayName: String?
        get() = user.displayName

    override val email: String?
        get() = user.email

    override val phoneNumber: String?
        get() = user.phoneNumber

    override val providerId: String
        get() = user.providerID

    override val tenantId: String?
        get() = user.tenantID

    override val uid: String
        get() = user.uid

    override val isEmailVerified: Boolean
        get() = user.isEmailVerified()

    override val providerData: List<KUserInfo>
        get() = TODO("Implement providerData for ios")

    override val isAnonymous: Boolean
        get() = user.isAnonymous()

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun linkWithCredential(credential: AuthCredential) = suspendCoroutine { cont ->
        if (credential !is AuthCredentialImpl)
            error("Illegal argument")

        user.linkWithCredential(credential.credential) { _, error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun delete() = suspendCoroutine { cont ->
        user.deleteWithCompletion { error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun getIdToken(forceRefresh: Boolean): GetTokenResult = suspendCoroutine { cont ->
        user.getIDTokenResultWithCompletion { it, error ->
            if (error == null) {
                cont.resumeWithException(error.toKotlin())
                return@getIDTokenResultWithCompletion
            } else if (it == null) {
                cont.resumeWithException(Exception("unknown"))
                return@getIDTokenResultWithCompletion
            }

            val result = GetTokenResult(
                token = it.token,
                claims = it.claims.let {
                    val data = hashMapOf<String?, Any?>()
                    it.forEach { d ->
                        if (d.key != null) {
                            data[d.key.toString()] = d.value
                        }
                    }
                    data
                },
                authTimestamp = it.authDate.timeIntervalSince1970.toLong(),
                signInProvider = it.signInProvider,
                issuedAtTimestamp = it.issuedAtDate.timeIntervalSince1970.toLong(),
                expirationTimestamp = it.expirationDate.timeIntervalSince1970.toLong(),
                signInSecondFactor = it.signInSecondFactor
            )

            cont.resume(result)
        }
    }

    override suspend fun reauthenticate(credential: AuthCredential) = suspendCoroutine { cont ->
        if (credential !is AuthCredentialImpl)
            error("Illegal argument")

        user.reauthenticateWithCredential(credential.credential) { _, error ->
            if (error == null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun reload() = suspendCoroutine { cont ->
        user.reloadWithCompletion { error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun sendEmailVerification() = suspendCoroutine { cont ->
        user.sendEmailVerificationWithCompletion { error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun unlink(provider: String) = suspendCoroutine { cont ->
        user.unlinkFromProvider(provider) { _, error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun updateEmail(email: String) = suspendCoroutine { cont ->
        user.updateEmail(email) { error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun updatePassword(password: String) = suspendCoroutine { cont ->
        user.updatePassword(password) { error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun updatePhoneNumber(phoneAuthCredential: PhoneAuthCredential) = suspendCoroutine { cont ->
        if (phoneAuthCredential !is PhoneAuthCredentialImpl)
            error("Illegal argument")

        user.updatePhoneNumberCredential(phoneAuthCredential.credential) { error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

    override suspend fun reauthenticateWithProvider(activity: KActivity, provider: OAuthProvider) =
        suspendCoroutine { cont ->
            val platformProvider = FIROAuthProvider.providerWithProviderID(provider.providerID).apply {
                setCustomParameters(provider.customParameters as Map<Any?, *>)
                setScopes(provider.scopes)
            }

            user.reauthenticateWithProvider(platformProvider, null) { _, error ->
                if (error != null) {
                    cont.resumeWithException(error.toKotlin())
                } else {
                    cont.resume(Unit)
                }
            }
        }

    override suspend fun linkWithProvider(activity: KActivity, provider: OAuthProvider) = suspendCoroutine { cont ->
        val platformProvider = FIROAuthProvider.providerWithProviderID(provider.providerID).apply {
            setCustomParameters(provider.customParameters as Map<Any?, *>)
            setScopes(provider.scopes)
        }

        user.linkWithProvider(platformProvider, null) { _, error ->
            if (error != null) {
                cont.resumeWithException(error.toKotlin())
            } else {
                cont.resume(Unit)
            }
        }
    }

}