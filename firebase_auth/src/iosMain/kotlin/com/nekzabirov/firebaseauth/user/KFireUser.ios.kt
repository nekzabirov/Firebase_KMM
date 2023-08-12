package com.nekzabirov.firebaseauth.user

import cocoapods.FirebaseAuth.FIRUser
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class KFireUser internal constructor(private val platform: FIRUser) {
    actual val uid: String
        get() = platform.uid()

    actual val displayName: String?
        get() = platform.displayName()

    actual val email: String?
        get() = platform.email()

    actual val phoneNumber: String?
        get() = platform.phoneNumber()

    actual val photoURL: String?
        get() = platform.photoURL()?.absoluteString

    actual val isEmailVerified: Boolean
        get() = platform.isEmailVerified()

    actual suspend fun delete() = suspendCoroutine { cont ->
        platform.deleteWithCompletion { error ->
            if (error != null)
                cont.resumeWithException(Exception(error.localizedDescription()))
            else
                cont.resume(Unit)
        }
    }

    actual suspend fun reload() = suspendCoroutine { cont ->
        platform.reloadWithCompletion { error ->
            if (error != null)
                cont.resumeWithException(Exception(error.localizedDescription()))
            else
                cont.resume(Unit)
        }
    }

    actual suspend fun updateProfile(request: UserUpdateProfileRequest) = suspendCoroutine { cont ->
        platform.profileChangeRequest().apply {
            setDisplayName(request.displayName)
            setPhotoURL(request.photoURL)
        }.commitChangesWithCompletion { error ->
            if (error != null)
                cont.resumeWithException(Exception(error.localizedDescription()))
            else
                cont.resume(Unit)
        }
    }
}