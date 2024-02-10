package com.nekzabirov.firebaseauth.credential

/**
 * Interface representing an authentication credential.
 * used in [com.nekzabirov.firebaseauth.KFirebaseAuth.signInWithCredential].
 */
interface AuthCredential {
    /** The provider identifier associated with this credential (e.g., "email", "facebook.com"). */
    val provider: String

    /** The sign-in method associated with this credential (e.g., "password", "phone"). */
    val signInMethod: String
}
