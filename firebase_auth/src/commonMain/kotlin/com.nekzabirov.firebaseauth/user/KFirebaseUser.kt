package com.nekzabirov.firebaseauth.user

import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.PhoneAuthCredential
import com.nekzabirov.firebaseauth.provider.OAuthProvider

interface KFirebaseUser : KUserInfo {
    override val photoUrl: String?

    override val displayName: String?

    override val email: String?

    override val phoneNumber: String?

    override val providerId: String

    val tenantId: String?

    override val uid: String

    override val isEmailVerified: Boolean

    val providerData: List<KUserInfo>

    val isAnonymous: Boolean

    suspend fun linkWithCredential(credential: AuthCredential)

    suspend fun delete()

    suspend fun getIdToken(forceRefresh: Boolean): GetTokenResult

    suspend fun reauthenticate(credential: AuthCredential)

    suspend fun reload()

    suspend fun sendEmailVerification()

    suspend fun linkWithProvider(activity: KActivity, provider: OAuthProvider)

    suspend fun reauthenticateWithProvider(activity: KActivity, provider: OAuthProvider)

    suspend fun unlink(provider: String)

    suspend fun updateEmail(email: String)

    suspend fun updatePassword(password: String)

    suspend fun updatePhoneNumber(phoneAuthCredential: PhoneAuthCredential)
}