package com.nekzabirov.firebaseauth.user

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

actual class KFireUser internal constructor(private val platform: FirebaseUser) {
    actual val uid: String
        get() = platform.uid

    actual val displayName: String?
        get() = platform.displayName

    actual val email: String?
        get() = platform.email

    actual val phoneNumber: String?
        get() = platform.phoneNumber

    actual val photoURL: String?
        get() = platform.photoUrl?.toString()

    actual val isEmailVerified: Boolean
        get() = platform.isEmailVerified

    actual suspend fun delete() = platform.delete().await().run { }

    actual suspend fun reload() = platform.reload().await().run {  }

    actual suspend fun updateProfile(request: UserUpdateProfileRequest) = platform.updateProfile(request.build()).await().run {  }
}