package com.nekzabirov.firebaseauth.user

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl
import com.nekzabirov.firebaseauth.credential.PhoneAuthCredential
import com.nekzabirov.firebaseauth.credential.PhoneAuthCredentialImpl
import com.nekzabirov.firebaseauth.provider.OAuthProvider
import kotlinx.coroutines.tasks.await

class KFirebaseUserImpl internal constructor(private val user: FirebaseUser) : KFirebaseUser {
    override val photoUrl: String?
        get() = user.photoUrl?.toString()
    override val displayName: String?
        get() = user.displayName
    override val email: String?
        get() = user.email
    override val phoneNumber: String?
        get() = user.phoneNumber
    override val providerId: String
        get() = user.providerId
    override val tenantId: String?
        get() = user.tenantId
    override val uid: String
        get() = user.uid
    override val providerData: List<KUserInfo>
        get() = user.providerData.map {
            object : KUserInfo {
                override val photoUrl: String?
                    get() = it.photoUrl?.toString()
                override val displayName: String?
                    get() = it.displayName
                override val email: String?
                    get() = it.email
                override val phoneNumber: String?
                    get() = it.phoneNumber
                override val providerId: String
                    get() = it.providerId
                override val uid: String
                    get() = it.uid
                override val isEmailVerified: Boolean
                    get() = it.isEmailVerified

            }
        }
    override val isAnonymous: Boolean
        get() = user.isAnonymous
    override val isEmailVerified: Boolean
        get() = user.isEmailVerified

    override suspend fun linkWithCredential(credential: AuthCredential) {
        if (credential !is AuthCredentialImpl)
            error("Illegal argument")

        user.linkWithCredential(credential.credential).await()
    }

    override suspend fun delete() {
        user.delete().await()
    }

    override suspend fun getIdToken(forceRefresh: Boolean): GetTokenResult =
        user.getIdToken(forceRefresh).await().let {
            GetTokenResult(
                token = it.token,
                claims = it.claims,
                authTimestamp = it.authTimestamp,
                signInProvider = it.signInProvider,
                issuedAtTimestamp = it.issuedAtTimestamp,
                expirationTimestamp = it.expirationTimestamp,
                signInSecondFactor = it.signInSecondFactor
            )
        }

    override suspend fun reauthenticate(credential: AuthCredential) {
        if (credential !is AuthCredentialImpl)
            error("Illegal argument")

        user.reauthenticate(credential.credential).await()
    }

    override suspend fun reload() {
        user.reload().await()
    }

    override suspend fun sendEmailVerification() {
        user.sendEmailVerification().await()
    }

    override suspend fun unlink(provider: String) {
        user.unlink(provider).await()
    }

    override suspend fun updateEmail(email: String) {
        user.updateEmail(email).await()
    }

    override suspend fun updatePassword(password: String) {
        user.updatePassword(password).await()
    }

    override suspend fun updatePhoneNumber(phoneAuthCredential: PhoneAuthCredential) {
        if (phoneAuthCredential !is PhoneAuthCredentialImpl)
            error("Illegal argument")

        user.updatePhoneNumber(phoneAuthCredential.credential).await()
    }

    override suspend fun reauthenticateWithProvider(activity: KActivity, provider: OAuthProvider) {
        val platformProvider = com.google.firebase.auth.OAuthProvider.newBuilder(provider.providerID)
            .addCustomParameters(provider.customParameters)
            .setScopes(provider.scopes)
            .build()

        user.startActivityForReauthenticateWithProvider(activity, platformProvider).await()
    }

    override suspend fun linkWithProvider(activity: KActivity, provider: OAuthProvider) {
        val platformProvider = com.google.firebase.auth.OAuthProvider.newBuilder(provider.providerID)
            .addCustomParameters(provider.customParameters)
            .setScopes(provider.scopes)
            .build()

        user.startActivityForLinkWithProvider(activity, platformProvider).await()
    }

    override suspend fun updateProfile(request: KUserProfileChangeRequest) {
        val platformRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(request.displayName)
            .setPhotoUri(Uri.parse(request.photoUrl))
            .build()

        user.updateProfile(platformRequest).await()
    }
}