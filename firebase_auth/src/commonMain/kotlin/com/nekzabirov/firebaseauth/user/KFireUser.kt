package com.nekzabirov.firebaseauth.user

expect class KFireUser {
    val uid: String

    val displayName: String?

    val email: String?

    val phoneNumber: String?

    val photoURL: String?

    val isEmailVerified: Boolean

    suspend fun delete()

    suspend fun reload()

    suspend fun updateProfile(request: UserUpdateProfileRequest)
}