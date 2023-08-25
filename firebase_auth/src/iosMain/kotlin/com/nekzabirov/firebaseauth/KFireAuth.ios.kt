package com.nekzabirov.firebaseauth

import cocoapods.FirebaseAuth.*
import cocoapods.GoogleSignIn.GIDGoogleUser
import com.nekzabirov.firebaseapp.AuthFail
import com.nekzabirov.firebaseauth.user.KFireUser
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class KFireAuth {
    actual inner class Phone {
        actual suspend fun verifyPhoneNumber(phoneNumber: String): String = suspendCoroutine { cont ->
            FIRPhoneAuthProvider
                .provider()
                .verifyPhoneNumber(
                    phoneNumber = phoneNumber,
                    UIDelegate = null
                ) { verificationId, error ->
                    if (verificationId != null)
                        cont.resume(verificationId)
                    else if (error != null)
                        cont.resumeWithException(AuthFail(error.localizedDescription))
                    else
                        cont.resumeWithException(AuthFail("Unknown error"))
                }
        }

        actual suspend fun getCredential(verificationID: String, verificationCode: String): KFireCredential {
            return FIRPhoneAuthProvider.provider()
                .credentialWithVerificationID(
                    verificationID = verificationID,
                    verificationCode = verificationCode
                ).let {
                    FireCredential(it)
                }
        }
    }

    actual inner class Google internal actual constructor() {
        private var lastUser: GIDGoogleUser? = null

        /*suspend fun request(clientID: String): String = suspendCoroutine { cont ->
            GIDSignIn.sharedInstance.configuration = GIDConfiguration(clientID)

            GIDSignIn.sharedInstance.signInWithPresentingViewController(activity) { result, error ->
                if (error != null)
                    cont.resumeWithException(AuthFail(error.localizedDescription))
                else if (result?.user?.idToken != null) {
                    lastUser = result.user
                    cont.resume(result.user.idToken!!.tokenString)
                } else
                    cont.resumeWithException(AuthFail("User declined Google sign in"))
            }
        }*/

        actual suspend fun getCredential(idToken: String): KFireCredential {
            return FireCredential(
                FIRGoogleAuthProvider.credentialWithIDToken(
                    idToken,
                    lastUser!!.accessToken.tokenString
                )
            )
        }
    }

    actual inner class Facebook internal actual constructor() {
       /* actual suspend fun request(): String = suspendCoroutine { cont ->
            FBSDKLoginManager().logInWithPermissions(
                listOf("public_profile"),
                activity
            ) { result, error ->
                if (error != null)
                    cont.resumeWithException(AuthFail(error.localizedDescription))
                else if (result?.authenticationToken() != null)
                    cont.resume(result.authenticationToken()!!.tokenString())
                else
                    cont.resumeWithException(AuthFail("User declined Facebook login"))
            }
        }*/

        actual suspend fun getCredential(accessToken: String): KFireCredential {
            return FIRFacebookAuthProvider.credentialWithAccessToken(accessToken).let {
                FireCredential(it)
            }
        }
    }

    actual companion object {
        actual fun getInstance(): KFireAuth {
            return KFireAuth()
        }
    }

    private val firAuth: FIRAuth = FIRAuth.auth()

    actual val currentUser: KFireUser?
        get() = firAuth.currentUser()?.let { KFireUser(it) }

    actual val phone: Phone by lazy { Phone() }

    actual val google: Google by lazy { Google() }

    actual val facebook: Facebook by lazy { Facebook() }

    actual suspend fun signInWithCredential(credential: KFireCredential): Boolean = suspendCoroutine { cont ->
        firAuth.signInWithCredential(credential.platform) { result, error ->
            if (error != null)
                cont.resumeWithException(AuthFail(error.localizedDescription))
            else if (result?.user != null)
                cont.resume(true)
            else
                cont.resume(false)
        }
    }
}

actual abstract class KFireCredential internal constructor(val platform: FIRAuthCredential)

private class FireCredential(platform: FIRAuthCredential) : KFireCredential(platform)